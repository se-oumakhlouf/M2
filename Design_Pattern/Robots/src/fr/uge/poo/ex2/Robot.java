package fr.uge.poo.ex2;

import fr.uge.poo.ex2.strategy.TargetStrategy;

import java.util.*;


// Question 2 -> Design Pattern : Builder
// Question 4 -> Design Pattern : Strategy
// Question 7 -> Design Pattern : Decorator
public class Robot {

  private final String name;
  private final int weight;
  private final int attack;
  private final int defense;
  private final int speed;
  private final List<String> sponsors;
  private final TargetStrategy strategy;
  private int health = 100;

  public static class RobotBuilder {
    private List<String> sponsors = new ArrayList<>();
    private String name;
    private int weight;
    private int attack;
    private int defense;
    private int speed;
    private TargetStrategy strategy = targets -> {
      if (targets.isEmpty()) return Optional.empty();
      return Optional.ofNullable(targets.getFirst());
    };

    private boolean isValueInRange(int value) {
      return value >= 0 && value <= 100;
    }

    public RobotBuilder name(String name) {
      this.name = Objects.requireNonNull(name);
      return this;
    }

    public RobotBuilder weight(int weight) {
      if (weight < 0) { throw new IllegalArgumentException("Weight must be positive"); }
      this.weight = weight;
      return this;
    }

    public RobotBuilder attack(int attack) {
      if (!isValueInRange(attack)) { throw new IllegalArgumentException("Attack must be between 0 and 100"); }
      this.attack = attack;
      return this;
    }

    public RobotBuilder defense(int defense) {
      if (!isValueInRange(defense)) { throw new IllegalArgumentException("Defense must be between 0 and 100"); }
      this.defense = defense;
      return this;
    }

    public RobotBuilder speed(int speed) {
      if (!isValueInRange(speed)) { throw new IllegalArgumentException("Speed must be between 0 and 100"); }
      this.speed = speed;
      return this;
    }

    public RobotBuilder sponsors(List<String> sponsors) {
      this.sponsors = List.copyOf(Objects.requireNonNull(sponsors));
      return this;
    }

    public RobotBuilder targetStrategy(TargetStrategy strategy) {
      this.strategy = Objects.requireNonNull(strategy);
      return this;
    }

    public Robot build() {
      Objects.requireNonNull(name);

      int sum = attack + defense + speed;
      int limit = (weight < 1000) ? 100 : 200;
      if (sum > limit) {
        throw new IllegalStateException("Sum of stats exceeds limit for this weight");
      }

      if (weight >= 1000 && sponsors.isEmpty()) {
        throw new IllegalStateException("Robots over 1000g must have at least one sponsor");
      }

      return new Robot(name, weight, attack, defense, speed, sponsors, strategy);
    }
  }

  public static RobotBuilder with() {
    return new RobotBuilder();
  }

  private Robot(String name, int weight, int attack, int defense, int speed, List<String> sponsors, TargetStrategy strategy) {
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
    if (strategy == null) {
      strategy = targets -> {
        if (targets.isEmpty()) return Optional.empty();
        return Optional.ofNullable(targets.getFirst());
      };
    }
    this.strategy = strategy;
  }

  // Getters
  public String name() { return name; }
  public int weight() { return weight; }
  public int attack() { return attack; }
  public int defense() { return defense; }
  public int speed() { return speed; }
  public int life() { return health; }

  public List<String> sponsors() {
    return Collections.unmodifiableList(sponsors);
  }

  public Optional<Robot> chooseTarget(List<Robot> targets) {
    Objects.requireNonNull(targets);
    if (targets.isEmpty()) { return Optional.empty(); }
    return strategy.selectTarget(targets);
  }

  public boolean isAlive() {
    return health > 0;
  }

  public void takeDamage(int damage) {
    if (damage < 0) { return; }
    health = Math.max(0, health - damage);
  }

  @Override
  public String toString() {
    return "Robot -> Name: " + name + ", Heatlh: " + health
               + ", Weight: " + weight
               + ", Attack: " + attack + ", Defense: " + defense
               + ", Speed: " + speed + ", Sponsors: " + sponsors;
  }

}
