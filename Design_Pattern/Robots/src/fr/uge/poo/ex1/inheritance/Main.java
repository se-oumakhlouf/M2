package fr.uge.poo.ex1.inheritance;

import java.util.List;

public class Main {

  public static void main(String[] args) {

    Robot bob = new RandomTargetRobot("Bob", 890, 9, 90, 1, List.of("IGM", "ESIEE"));
    Robot alice = new MinDefenseTargetRobot("Alice", 2025, 30, 100, 70, List.of("Valve", "Gaben"));

    Robot bumblebee = new RandomTargetRobot("Bumblebee", 100, 10, 50, 10, List.of());
    Robot ultron = new MinDefenseTargetRobot("Ultron", 1000, 100, 100, 0, List.of("Avengers"));
    Robot r2d2 = new MinDefenseTargetRobot("R2D2", 32, 10, 10, 10, List.of());

    List<Robot> targets = List.of(alice, bob, ultron, bumblebee, r2d2);

    System.out.println("Bob's (random) Target : " + bob.chooseTarget(targets));
    System.out.println("Alice's (min def) Target : " + alice.chooseTarget(targets));
  }
}
