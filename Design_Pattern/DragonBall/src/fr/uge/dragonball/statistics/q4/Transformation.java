package fr.uge.dragonball.statistics.q4;

import java.util.HashSet;
import java.util.Objects;
import java.util.function.ToIntFunction;

public class Transformation {

  private static final HashSet<String> NAMES = new HashSet<>();

  private final String name;
  private final ToIntFunction<Fighter> powerTransform;
  private final ToIntFunction<Fighter> maxHealthTransform;

  public Transformation(String name, ToIntFunction<Fighter> powerTransform, ToIntFunction<Fighter> maxHealthTransform) {
    Objects.requireNonNull(name);
    Objects.requireNonNull(powerTransform);
    Objects.requireNonNull(maxHealthTransform);
    if (NAMES.contains(name)) throw new IllegalArgumentException("Name already used -> " + name);
    NAMES.add(name);
    this.name = name;
    this.powerTransform = powerTransform;
    this.maxHealthTransform = maxHealthTransform;
  }

  public String name() {
    return name;
  }

  public int power(Fighter fighter) {
    Objects.requireNonNull(fighter);
    return powerTransform.applyAsInt(fighter);
  }

  public int maxHealth(Fighter fighter) {
    Objects.requireNonNull(fighter);
    return maxHealthTransform.applyAsInt(fighter);
  }

}
