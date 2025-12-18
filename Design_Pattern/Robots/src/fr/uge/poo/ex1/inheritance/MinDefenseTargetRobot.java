package fr.uge.poo.ex1.inheritance;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MinDefenseTargetRobot extends Robot {

  public MinDefenseTargetRobot(String name, int weight, int attack, int defense, int speed, List<String> sponsors) {
    super(name, weight, attack, defense, speed, sponsors);
  }

  @Override
  public Optional<Robot> chooseTarget(List<Robot> targets) {
    Objects.requireNonNull(targets);
    if (targets.isEmpty()) return Optional.empty();
    return targets.stream().min(Comparator.comparingInt(Robot::defense));
  }

  @Override
  public String toString() {
    return "MinDefenseTargetRobot -> " + super.toString();
  }
}
