package fr.uge.dragonball.statistics.q4;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public record FusedFighter(List<Fighter> fighters) implements Fighter {

  public FusedFighter {
    Objects.requireNonNull(fighters);
    if (fighters.size() < 2) throw new IllegalArgumentException("At least 2 fighters must be provided");
  }

  @Override
  public String name() {
    return fighters.stream().map(Fighter::name).collect(Collectors.joining("-", "<", ">"));
  }

  @Override
  public int power() {
    return fighters.stream().mapToInt(Fighter::power).sum();
  }

  @Override
  public int maxHealth() {
    return fighters.stream().mapToInt(Fighter::maxHealth).min().getAsInt();
  }
}
