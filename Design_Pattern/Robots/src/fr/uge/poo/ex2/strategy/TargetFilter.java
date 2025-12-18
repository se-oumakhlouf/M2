package fr.uge.poo.ex2.strategy;

import fr.uge.poo.ex2.Robot;

import java.util.List;

@FunctionalInterface
public interface TargetFilter {
  List<Robot> filter(Robot attacker, List<Robot> targets);

  static TargetFilter identity() { return (attacker, targets) -> targets; }
}
