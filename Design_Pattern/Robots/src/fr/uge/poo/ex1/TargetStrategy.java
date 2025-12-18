package fr.uge.poo.ex1;

import java.util.List;
import java.util.Optional;

@FunctionalInterface
public interface TargetStrategy {
  Optional<Robot> selectTarget(List<Robot> targets);
}
