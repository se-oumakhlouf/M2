package fr.uge.poo.ex2.strategy;

import fr.uge.poo.ex2.Robot;

import java.util.List;
import java.util.Optional;

@FunctionalInterface
public interface TargetStrategy {
  Optional<Robot> selectTarget(List<Robot> targets);
}
