package fr.uge.dragonball.statistics.q3;

import java.util.List;
import java.util.Objects;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public record BasicFighter(String name, int power, int maxHealth) implements Fighter {

  public BasicFighter {
    Objects.requireNonNull(name);
    if (power <= 0) throw new IllegalArgumentException("Power must be positive");
    if (maxHealth <= 0) throw new IllegalArgumentException("MaxHealth must be positive");
  }

  public static BasicFighter fusion(List<Fighter> fighters) {
    Objects.requireNonNull(fighters);
    if (fighters.size() < 2) throw new IllegalArgumentException("At least 2 fighters must be provided");

    return new BasicFighter(
        fighters.stream().map(Fighter::name).collect(Collectors.joining("-", "<", ">")),
        fighters.stream().mapToInt(Fighter::power).sum(),
        fighters.stream().mapToInt(Fighter::maxHealth).min().getAsInt());
  }

  @Override
  public String toString() {
    return "Name: " + name + ", Power: " + power + ", MaxHealth: " + maxHealth;
  }

  public BasicFighter transform(String name, ToIntFunction<BasicFighter> powerTransform, ToIntFunction<BasicFighter> maxHealthTransform) {
    return new BasicFighter(this.name + " as " + name, powerTransform.applyAsInt(this), maxHealthTransform.applyAsInt(this));
  }

}
