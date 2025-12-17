package fr.uge.dragonball.training.q1;

@FunctionalInterface
public interface ExitStrategy {
  boolean shouldExit(TrainingRoom.Fighter fighter, int timeSpent);
}
