package fr.uge.dragonball.statistics;

import java.util.Objects;

public record BasicFighter(String name, int power, int maxHealth) implements Fighter {

  public BasicFighter {
    Objects.requireNonNull(name);
    if (power <= 0) throw new IllegalArgumentException("Power must be positive");
    if (maxHealth <= 0) throw new IllegalArgumentException("MaxHealth must be positive");
  }

  @Override
  public String toString() {
    return "Name: " + name + ", Power: " + power + ", MaxHealth: " + maxHealth;
  }

}
