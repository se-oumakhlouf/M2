package fr.uge.dragonball.statistics.q2;

import java.util.Objects;
import java.util.function.Function;

public record Fighter(String name, int power, int maxHealth) {

  public Fighter fuse(Fighter fighter) {
    Objects.requireNonNull(fighter);
    return new Fighter(concatName(this.name, fighter.name), this.power + fighter.power, Integer.min(this.maxHealth, fighter.maxHealth));
  }

  public Fighter transform(String name, Function<Fighter, Integer> power, Function<Fighter, Integer> maxHealth) {
    return new Fighter(this.name + " as " + name, power.apply(this), maxHealth.apply(this));
  }

  private String concatName(String name1, String name2) {
    return "<" + name1 + "-" + name2 + ">";
  }

  @Override
  public String toString() {
    return "Name: " + name + ", Power: " + power + ", MaxHealth: " + maxHealth;
  }
}
