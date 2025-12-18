package fr.uge.poo.ex2;

import fr.uge.poo.ex2.observer.FacebookObserver;
import fr.uge.poo.ex2.observer.MailObserver;
import fr.uge.poo.ex2.observer.TwitterObserver;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Main {

  public static void main(String[] args) {

    Robot bob = Robot.with()
                    .name("Bob")
                    .weight(890)
                    .speed(10)
                    .defense(10)
                    .attack(50)
                    .sponsors(List.of("IGM", "ESIEE", "Epic Games", "Remi Forax"))
                    .targetStrategy(targets -> {
                      int randomIndex = (int) (Math.random() * targets.size());
                      return Optional.ofNullable(targets.get(randomIndex));
                    })
                    .build();

    Robot alice = Robot.with()
                      .name("Alice")
                      .weight(2025)
                      .speed(20)
                      .defense(30)
                      .attack(40)
                      .sponsors(List.of("Micromania", "Riot Games", "Valve", "Gaben", "Shroud"))
                      .targetStrategy(targets -> targets.stream().min(Comparator.comparingInt(Robot::defense)))
                      .build();

    Robot charlie = Robot.with()
                        .name("Charlie")
                        .weight(10_000)
                        .speed(10)
                        .attack(50)
                        .defense(25)
                        .sponsors(List.of("Steve Wozniak", "ECorp"))
                        .build();

    Fight fight = new Fight();
    fight.register(bob);
    fight.register(alice);
    fight.register(charlie);

    fight.setTargetFilter(((_, targets) -> {
      List<Robot> nonEcorp = targets.stream()
                                 .filter(r -> !r.sponsors().contains("ECorp"))
                                 .toList();
      return nonEcorp.isEmpty() ? targets : nonEcorp;
    }));

    fight.register(new FacebookObserver());
    fight.register(new TwitterObserver());
    fight.register(new MailObserver());

    fight.start();

    fight.winner().ifPresentOrElse(
        winner -> System.out.println("The winner is " + winner.name() + " with " + winner.life() + " hp remaining"),
        () -> System.out.println("The fight ended in a draw or no one survived")
    );

  }
}
