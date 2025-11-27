import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Main {

  // Configuration PostgreSQL
  private static final String PG_URL = "jdbc:postgresql://localhost:5487/redisdb";
  private static final String PG_USER = "user";
  private static final String PG_PASSWORD = "password";

  // Pool Redis
  private static final JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), "localhost");

  public static void main(String[] args) {
    try {
      System.out.println("=== Chargement des données dans Redis ===");
      loadMedicamentsToRedis();
      System.out.println("\n=== Test de performance ===");
      int testCis = 60117855;
      testPostgresPerformance(testCis);
      testRedisPerformance(testCis);
    } finally {
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

        // Créer une map avec les données
        Map<String, String> medicament = new HashMap<>();
        medicament.put("cis", String.valueOf(cis));
        medicament.put("cip7", String.valueOf(rs.getInt("cip7")));
        medicament.put("cip13", String.valueOf(rs.getLong("cip13")));
        medicament.put("prese", rs.getString("prese"));
        medicament.put("statadm", rs.getString("statadm"));
        medicament.put("etacom", rs.getString("etacom"));

        Date datedecl = rs.getDate("datedecl");
        if (datedecl != null) {
          medicament.put("datedecl", datedecl.toString());
        }

        medicament.put("agrcol", rs.getString("agrcol"));
        medicament.put("prixe", String.valueOf(rs.getDouble("prixe")));
        medicament.put("liste", String.valueOf(rs.getInt("liste")));
        medicament.put("taux", String.valueOf(rs.getInt("taux")));
        medicament.put("fprixe", String.valueOf(rs.getDouble("fprixe")));
        medicament.put("disp", String.valueOf(rs.getDouble("disp")));

        // Stocker dans Redis
        jedis.hset(key, medicament);
        count++;
      }

      System.out.println("Chargé " + count + " médicaments dans Redis");

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private static void testPostgresPerformance(int cis) {
    long startTime = System.currentTimeMillis();

    String sql = "SELECT * FROM public.bdpm_ciscip2noidx WHERE cis = ?";

    try (Connection conn = DriverManager.getConnection(PG_URL, PG_USER, PG_PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, cis);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        String prese = rs.getString("prese");
        double prixe = rs.getDouble("prixe");
        System.out.println("PostgreSQL - Médicament: " + prese + ", Prix: " + prixe);
      } else {
        System.out.println("PostgreSQL - Médicament non trouvé");
      }

      rs.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    long endTime = System.currentTimeMillis();
    long duration = endTime - startTime;
    System.out.println("Durée PostgreSQL (connexion → fermeture): " + duration + " ms");
  }

  private static void testRedisPerformance(int cis) {
    long startTime = System.currentTimeMillis();

    try (Jedis jedis = jedisPool.getResource()) {
      String key = "medicament:" + cis;
      Map<String, String> medicament = jedis.hgetAll(key);

      if (!medicament.isEmpty()) {
        String prese = medicament.get("prese");
        String prixe = medicament.get("prixe");
        System.out.println("Redis - Médicament: " + prese + ", Prix: " + prixe);
      } else {
        System.out.println("Redis - Médicament non trouvé");
        
      }
    }

    long endTime = System.currentTimeMillis();
    long duration = endTime - startTime;
    System.out.println("Durée Redis (pool → requête → libération): " + duration + " ms");
  }

  private static void cacheMiss(int cis) {
    String sql = "SELECT * FROM public.bdpm_ciscip2noidx WHERE cis = ?";

    try (Connection conn = DriverManager.getConnection(PG_URL, PG_USER, PG_PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(sql);
         Jedis jedis = jedisPool.getResource()) {  // ✅ try-with-resources pour Jedis

      stmt.setInt(1, cis);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        // Récupérer toutes les données
        Map<String, String> medicament = new HashMap<>();
        medicament.put("cis", String.valueOf(rs.getInt("cis")));
        medicament.put("cip7", String.valueOf(rs.getInt("cip7")));
        medicament.put("cip13", String.valueOf(rs.getLong("cip13")));
        medicament.put("prese", rs.getString("prese"));
        medicament.put("statadm", rs.getString("statadm"));
        medicament.put("etacom", rs.getString("etacom"));

        Date datedecl = rs.getDate("datedecl");
        if (datedecl != null) {
          medicament.put("datedecl", datedecl.toString());
        }

        medicament.put("agrcol", rs.getString("agrcol"));
        medicament.put("prixe", String.valueOf(rs.getDouble("prixe")));
        medicament.put("liste", String.valueOf(rs.getInt("liste")));
        medicament.put("taux", String.valueOf(rs.getInt("taux")));
        medicament.put("fprixe", String.valueOf(rs.getDouble("fprixe")));
        medicament.put("disp", String.valueOf(rs.getDouble("disp")));

        // Stocker dans Redis comme Hash (structure recommandée)
        String key = "medicament:" + cis;
        jedis.hset(key, medicament);

        System.out.println("Cache miss - Médicament chargé dans Redis: " + rs.getString("prese"));

      } else {
        System.out.println("Cache miss - Médicament non trouvé dans PostgreSQL");
      }

      rs.close();

    } catch (SQLException e) {
      System.err.println("Erreur lors du cache miss pour CIS " + cis);
      e.printStackTrace();
    }
  }

}