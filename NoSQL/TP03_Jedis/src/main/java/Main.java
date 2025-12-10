import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Tuple;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {

  private static final String PG_URL = "jdbc:postgresql://localhost:5487/redisdb";
  private static final String PG_USER = "user";
  private static final String PG_PASSWORD = "password";

  private static final JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), "localhost");

  public static void main(String[] args) {

    configureRedisForLRU();
    Scanner scanner = new Scanner(System.in);

    try {
      System.out.println("=== Chargement des données dans Redis ===");
      loadMedicamentsToRedis();

      while (true) {
        System.out.println("\n------------------------------------------------");
        System.out.print("Entrez un code CIS (ou 0 pour quitter) : ");

        if (scanner.hasNextInt()) {
          int cis = scanner.nextInt();

          if (cis == 0) {
            System.out.println("Fermeture du programme.");
            break;
          }

          System.out.println("\n=== Test pour CIS: " + cis + " ===");
          testPostgresPerformance(cis);
          testRedisPerformance(cis);

        } else {
          System.out.println("Veuillez entrer un nombre valide.");
          scanner.next();
        }
      }
    } finally {
      scanner.close();
      jedisPool.close();
    }
  }

  private static void loadMedicamentsToRedis() {
    String sql = "SELECT cis, cip7, cip13, prese, statadm, etacom, datedecl, " +
                 "agrcol, prixe, liste, taux, fprixe, disp " +
                 "FROM public.bdpm_ciscip2noidx LIMIT 100";

    try (Connection conn = DriverManager.getConnection(PG_URL, PG_USER, PG_PASSWORD);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql);
         Jedis jedis = jedisPool.getResource()) {

      int count = 0;
      while (rs.next()) {
        int cis = rs.getInt("cis");
        String key = "medicament:" + cis;
        Map<String, String> medicament = mapResultSetToHash(rs);

        jedis.hset(key, medicament);
        jedis.zadd("medicaments", 1, key);
        count++;
      }
      System.out.println("Chargé " + count + " médicaments dans Redis");

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private static void testPostgresPerformance(int cis) {
    long startTime = System.currentTimeMillis();
    String sql = "SELECT prese, prixe FROM public.bdpm_ciscip2noidx WHERE cis = ?";

    try (Connection conn = DriverManager.getConnection(PG_URL, PG_USER, PG_PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, cis);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        System.out.println("PostgreSQL - Trouvé: " + rs.getString("prese"));
      } else {
        System.out.println("PostgreSQL - Médicament non trouvé");
      }
      rs.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }
    System.out.println("Temps PostgreSQL : " + (System.currentTimeMillis() - startTime) + " ms");
  }

  private static void testRedisPerformance(int cis) {
    long startTime = System.currentTimeMillis();

    try (Jedis jedis = jedisPool.getResource()) {
      String key = "medicament:" + cis;
      Map<String, String> medicament = jedis.hgetAll(key);

      if (!medicament.isEmpty()) {
        String prese = medicament.get("prese");
        System.out.println("Redis - Trouvé (Cache Hit): " + prese);
        jedis.zincrby("medicaments", 1, key);
      } else {
        System.out.println("Redis - Non trouvé (Cache Miss)");
        cacheMiss(cis);
      }
    }
    System.out.println("Temps Redis global : " + (System.currentTimeMillis() - startTime) + " ms");
  }

  private static void cacheMiss(int cis) {
    String sql = "SELECT * FROM public.bdpm_ciscip2noidx WHERE cis = ?";

    try (Connection conn = DriverManager.getConnection(PG_URL, PG_USER, PG_PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(sql);
         Jedis jedis = jedisPool.getResource()) {

      stmt.setInt(1, cis);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        System.out.println(">> Donnée trouvée dans PostgreSQL. Mise à jour du cache...");

        long dbSize = jedis.dbSize();
        if (dbSize >= 50) {
          System.out.println(">> Cache plein (" + dbSize + " éléments). Lancement éviction LFU...");

          Set<String> keysToRemove = jedis.zrange("medicaments", 0, 10);

          for (String keyToRemove : keysToRemove) {
            jedis.zrem("medicaments", keyToRemove);
            jedis.del(keyToRemove);
            System.out.println("   - Éviction (Suppression) : " + keyToRemove);
          }
        }

        Map<String, String> medicament = mapResultSetToHash(rs);
        String key = "medicament:" + cis;

        jedis.hset(key, medicament);
        jedis.zadd("medicaments", 1, key);

        System.out.println(">> Nouveau médicament ajouté au cache : " + rs.getString("prese"));

        afficherZSet(jedis);

      } else {
        System.out.println(">> Cache Miss impossible à résoudre : ID inexistant dans PostgreSQL.");
      }

      rs.close();

    } catch (SQLException e) {
      System.err.println("Erreur SQL : " + e.getMessage());
    }
  }

  private static void afficherZSet(Jedis jedis) {
    System.out.println("\n[État du Classement LFU (ZSET)]");
    Set<Tuple> elements = jedis.zrangeWithScores("medicaments", 0, -1);

    if (elements.isEmpty()) {
      System.out.println("(Vide)");
    } else {
      for (Tuple tuple : elements) {
        System.out.printf("   Clé: %-20s | Fréquence (Score): %.0f%n", tuple.getElement(), tuple.getScore());
      }
    }
    System.out.println("------------------------------------------------");
  }

  private static Map<String, String> mapResultSetToHash(ResultSet rs) throws SQLException {
    Map<String, String> medicament = new HashMap<>();
    medicament.put("cis", String.valueOf(rs.getInt("cis")));
    medicament.put("cip7", String.valueOf(rs.getInt("cip7")));
    medicament.put("cip13", String.valueOf(rs.getLong("cip13")));
    medicament.put("prese", rs.getString("prese"));
    medicament.put("statadm", rs.getString("statadm"));
    medicament.put("etacom", rs.getString("etacom"));

    Date datedecl = rs.getDate("datedecl");
    if (datedecl != null) medicament.put("datedecl", datedecl.toString());

    medicament.put("agrcol", rs.getString("agrcol"));
    medicament.put("prixe", String.valueOf(rs.getDouble("prixe")));
    medicament.put("liste", String.valueOf(rs.getInt("liste")));
    medicament.put("taux", String.valueOf(rs.getInt("taux")));
    medicament.put("fprixe", String.valueOf(rs.getDouble("fprixe")));
    medicament.put("disp", String.valueOf(rs.getDouble("disp")));
    return medicament;
  }

  private static void configureRedisForLRU() {
    try (Jedis jedis = jedisPool.getResource()) {
      jedis.flushDB();
    } catch (Exception e) {
      System.err.println("Erreur config Redis: " + e.getMessage());
    }
  }
}