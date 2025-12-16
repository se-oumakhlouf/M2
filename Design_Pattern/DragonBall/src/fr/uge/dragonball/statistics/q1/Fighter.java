package fr.uge.dragonball.statistics.q1;

import java.util.Objects;

public record Fighter(String name, int power, int maxHealth) {

  public Fighter fuse(Fighter fighter) {
    Objects.requireNonNull(fighter);
    return new Fighter(concatName(this.name, fighter.name), this.power + fighter.power, Integer.min(this.maxHealth, fighter.maxHealth));
  }

  private String concatName(String name1, String name2) {
    return "<" + name1 + "-" + name2 + ">";
  }

  @Override
  public String toString() {
    return "Name: " + name + ", Power: " + power + ", MaxHealth: " + maxHealth;
  }
}
