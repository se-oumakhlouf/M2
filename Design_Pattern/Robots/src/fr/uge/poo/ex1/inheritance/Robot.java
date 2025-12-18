package fr.uge.poo.ex1.inheritance;

import fr.uge.poo.ex1.TargetStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


// Question 2 -> Design Pattern : Builder
// Question 4 -> Design Pattern : Strategy
public abstract class Robot {

  private final String name;
  private final int weight;
  private final int attack;
  private final int defense;
  private final int speed;
  private final List<String> sponsors;
  private int health = 100;

  protected Robot(String name, int weight, int attack, int defense, int speed, List<String> sponsors) {
    this.name = Objects.requireNonNull(name);

    int sum = attack + defense + speed;
    int limit = (weight < 1000) ? 100 : 200;
    if (sum > limit) {
      throw new IllegalStateException("Sum of stats exceeds limit for this weight");
    }

    if (weight >= 1000 && sponsors.isEmpty()) {
      throw new IllegalStateException("Robots over 1000g must have at least one sponsor");
    }

    this.weight = weight;
    this.attack = attack;
    this.defense = defense;
    this.speed = speed;
    this.sponsors = List.copyOf(Objects.requireNonNull(sponsors));
  }

  // Getters
  public String name() { return name; }
  public int weight() { return weight; }
  public int attack() { return attack; }
  public int defense() { return defense; }
  public int speed() { return speed; }
  public int life() { return health; }

  public abstract Optional<Robot> chooseTarget(List<Robot> targets);

  public boolean isAlive() {
    return health > 0;
  }

  public void takeDamage(int damage) {
    if (damage < 0) { return; }
    health = Math.max(0, health - damage);
  }

  @Override
  public String toString() {
    return "Name: " + name + ", Weight: " + weight
               + ", Attack: " + attack + ", Defense: " + defense
               + ", Speed: " + speed + ", Sponsors: " + sponsors;
  }

}
