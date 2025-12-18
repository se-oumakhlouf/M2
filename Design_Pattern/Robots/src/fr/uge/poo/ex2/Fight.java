package fr.uge.poo.ex2;

import fr.uge.poo.ex2.observer.FightObserver;
import fr.uge.poo.ex2.strategy.TargetFilter;

import java.util.*;

/*
 Question 3:
 Ce serait une mauvais idée de mettre toutes les fonctionnalités dans la classe Fight
 car cela ne respecte pas le Single Responsibility Principle des principes SOLID.
 Design Pattern : Observer

 Question 6 -> Design Pattern : Strategy
*/

public class Fight {

  private final Map<String, Robot> robotsMap = new HashMap<>();
  private Robot winner;
  private boolean started = false;
  private final List<FightObserver> observers = new ArrayList<>();
  private TargetFilter targetFilter = TargetFilter.identity();

  public void setTargetFilter(TargetFilter targetFilter) {
    this.targetFilter = Objects.requireNonNull(targetFilter);
  }

  public void register(Robot robot) {
    if (started) throw new IllegalStateException("Fight already started");
    Objects.requireNonNull(robot);
    robotsMap.putIfAbsent(robot.name(), robot);
  }

  public void register(FightObserver observer) {
    Objects.requireNonNull(observer);
    observers.add(observer);
  }

  public void start() {
    started = true;
    List<Robot> participants = new ArrayList<>(robotsMap.values().stream()
                                                   .filter(Robot::isAlive)
                                                   .sorted(Comparator.comparingInt(Robot::speed).reversed())
                                                   .toList());

    observers.forEach(obs -> obs.onFightStart(List.copyOf(participants)));


    for (int i = 1; i <= 100; i++) {
      List<Robot> aliveBefore = participants.stream().filter(Robot::isAlive).toList();
      if (participants.size() <= 1) break;

      for (Robot attacker : participants) {
        if (!attacker.isAlive()) continue;
        List<Robot> targets = participants.stream().filter(robot -> !robot.equals(attacker) && robot.isAlive()).toList();
        if (targets.isEmpty()) break;
        List<Robot> filteredTargets = targetFilter.filter(attacker, targets);
        Optional<Robot> optTarget = attacker.chooseTarget(filteredTargets);
        optTarget.ifPresent(target -> target.takeDamage(Math.max(0, attacker.attack() - target.defense())));
      }

      List<Robot> deadRobots = aliveBefore.stream().filter(robot -> !robot.isAlive()).toList();
      int currentRound = i;
      observers.forEach(obs -> obs.onRoundEnd(currentRound, deadRobots));

      participants.removeIf(r -> !r.isAlive());
    }

    if (participants.size() == 1) winner = participants.getFirst();
    observers.forEach(obs -> obs.onFightEnd(winner));
  }

  public Optional<Robot> winner() {
    return Optional.ofNullable(winner);
  }

}
