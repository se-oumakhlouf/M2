package fr.uge.dragonball.statistics.q2;

import java.util.List;

public class Application {

  public static void main(String[] args) {
    Fighter goku = new Fighter("Goku", 9_000, 10_000);
    Fighter vegeta = new Fighter("Vegeta", 8_500, 9_500);
    Fighter gohan = new Fighter("Gohan", 7_000, 3_500);
    Fighter trunks = new Fighter("Trunks", 6_500, 4_000);

    Fighter gokuSS = goku.transform("Super sayan", fighter -> fighter.power() * 2, fighter -> fighter.maxHealth() / 2);
    Fighter gokuVegetaSSB = goku.fuse(vegeta).transform("Super Sayan Blue", fighter -> fighter.power() * fighter.maxHealth(), _ -> 1);
    Fighter vegetaSSBGohanSS2 = vegeta.transform("Super Sayan Blue", fighter -> fighter.power() * fighter.maxHealth(), _ -> 1)
        .fuse(gohan.transform("Super Sayan Blue 2", fighter -> fighter.power() * 4, fighter -> fighter.maxHealth() / 4));

    List<Fighter> transformations = List.of(gokuSS, gokuVegetaSSB, vegetaSSBGohanSS2);
    System.out.println("With Transformations :");
    transformations.forEach(System.out::println);
  }
}
