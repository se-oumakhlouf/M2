package fr.uge.dragonball.statistics.q3;


import java.util.List;

// Design Pattern : Decorator
public class Application {

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

    Fighter gokuSS = new TransformedFighter(superSayan, goku);
    Fighter gokuVegeta = new TransformedFighter(superSayanBlue, BasicFighter.fusion(List.of(goku, vegeta)));
    Fighter vegetaSSBGohanSS2 = BasicFighter.fusion(List.of(new TransformedFighter(superSayanBlue, vegeta), new TransformedFighter(superSayanTwo, gohan)));

    System.out.println(gokuSS);
    System.out.println(gokuVegeta);
    System.out.println(vegetaSSBGohanSS2);



  }
}
