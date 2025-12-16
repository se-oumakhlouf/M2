package fr.uge.dragonball.statistics;


import java.util.List;

// Design Pattern : Decorator
public class Application {

  public static List<BasicFighter> extractBasicFighters(Fighter fighter) {
    return switch (fighter) {
      case BasicFighter basic -> List.of(basic);
      case TransformedFighter transformed -> extractBasicFighters(transformed.fighter());
      case FusedFighter fused -> fused.fighters().stream().flatMap(f -> extractBasicFighters(f).stream()).toList();
    };
  }

  public static void main(String[] args) {
    Fighter goku = new BasicFighter("Goku", 9_000, 10_000);
    Fighter vegeta = new BasicFighter("Vegeta", 8_500, 9_500);
    Fighter gohan = new BasicFighter("Gohan", 7_000, 3_500);
    Fighter trunks = new BasicFighter("Trunks", 6_500, 4_000);

    Transformation superSayan = new Transformation("Super Sayan", fighter -> fighter.power() * 2, fighter -> fighter.maxHealth() / 2);
    Transformation superSayanTwo = new Transformation("Super Sayan 2", fighter -> fighter.power() * 4, fighter -> fighter.maxHealth() / 4);
    Transformation superSayanBlue = new Transformation("Super Sayan Blue", fighter -> fighter.power() * fighter.maxHealth(), _ -> 1);

    try {
      new Transformation("Super Sayan", fighter -> fighter.power() * 10, fighter -> fighter.maxHealth() / 10);
    } catch (IllegalArgumentException e) {
      System.out.println("Tentative de doublon bloquée -> " + e.getMessage());
    }

    var megaFusion = new FusedFighter(List.of(goku, new TransformedFighter(superSayanTwo, vegeta), new TransformedFighter(superSayan, gohan), trunks));
    var megaFusionSS = new TransformedFighter(superSayanBlue, megaFusion);

    System.out.println(megaFusionSS);

    System.out.println("Basic fighters :");
    extractBasicFighters(megaFusionSS).forEach(System.out::println);
  }
}
