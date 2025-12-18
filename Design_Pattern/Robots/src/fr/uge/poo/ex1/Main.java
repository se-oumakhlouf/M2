package fr.uge.poo.ex1;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Main {

  public static void main(String[] args) {

    Robot bob = Robot.with()
                    .name("Bob")
                    .weight(890)
                    .speed(1)
                    .defense(90)
                    .attack(9)
                    .sponsors(List.of("IGM", "ESIEE", "Epic Games", "Remi Forax"))
                    .targetStrategy(targets -> {
                      int randomIndex = (int) (Math.random() * targets.size());
                      return Optional.ofNullable(targets.get(randomIndex));
                    })
                    .build();

    Robot alice = Robot.with()
                      .name("Alice")
                      .weight(2025)
                      .speed(70)
                      .defense(100)
                      .attack(30)
                      .sponsors(List.of("Micromania", "Riot Games", "Valve", "Gaben", "Shroud"))
                      .targetStrategy(targets -> targets.stream().min(Comparator.comparingInt(Robot::defense)))
                      .build();

    Robot bumblebee = Robot.with().name("Bumblebee").defense(60).build();
    Robot ultron = Robot.with().name("Ultron").defense(80).build();
    Robot r2d2 = Robot.with().name("R2D2").defense(10).build();

    System.out.println(bob);
    System.out.println(alice);

    List<Robot> targets = List.of(alice, bob, ultron, bumblebee, r2d2);

    System.out.println("Bob's (random) Target : " + bob.chooseTarget(targets));
    System.out.println("Alice's (min def) Target : " + alice.chooseTarget(targets));

    RobotLogger aliceLogger = new TargetLogger(alice);
    RobotLogger bobLogger = new DamageLogger(bob);

    aliceLogger.takeDamage(18);
    bobLogger.takeDamage(17);

    for (int i = 0; i < 10; i++) {
      aliceLogger.chooseTarget(targets);
      bobLogger.chooseTarget(targets);
    }
  }
}
