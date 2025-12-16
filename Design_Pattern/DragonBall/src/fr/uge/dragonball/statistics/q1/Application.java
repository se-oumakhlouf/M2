package fr.uge.dragonball.statistics.q1;

import java.util.List;

public class Application {

  public static void main(String[] args) {
    Fighter goku = new Fighter("Goku", 9_000, 10_000);
    Fighter vegeta = new Fighter("Vegeta", 8_500, 9_500);
    Fighter gohan = new Fighter("Gohan", 7_000, 3_500);
    Fighter trunks = new Fighter("Trunks", 6_500, 4_000);

    List<Fighter> fighters = List.of(goku, vegeta, gohan, trunks);

    System.out.println("Individual fighters :");
    fighters.forEach(System.out::println);

    System.out.println();

    List<Fighter> fusionFighters = List.of(goku.fuse(vegeta), trunks.fuse(gohan));
    System.out.println("Fusion fighters :");
    fusionFighters.forEach(System.out::println);

    List<Fighter> megaFusion = List.of(fusionFighters.get(0).fuse(fusionFighters.get(1)));

    System.out.println();

    System.out.println("Mega Fusion fighters :");
    megaFusion.forEach(System.out::println);
  }
}
