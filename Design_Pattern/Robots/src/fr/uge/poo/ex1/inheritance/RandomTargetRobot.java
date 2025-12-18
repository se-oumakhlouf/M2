package fr.uge.poo.ex1.inheritance;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class RandomTargetRobot extends Robot{

  public RandomTargetRobot(String name, int weight, int attack, int defense, int speed, List<String> sponsors) {
    super(name, weight, attack, defense, speed, sponsors);
  }

  @Override
  public Optional<Robot> chooseTarget(List<Robot> targets) {
    Objects.requireNonNull(targets);
    if (targets.isEmpty()) return Optional.empty();
    int randomIndex = (int) (Math.random() * targets.size());
    return Optional.ofNullable(targets.get(randomIndex));
  }

  @Override
  public String toString() {
    return "RandomTargetRobot -> " + super.toString();
  }

}
