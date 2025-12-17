package fr.uge.dragonball.training.q2;

@FunctionalInterface
public interface ExitStrategy {
  boolean shouldExit(TrainingRoom.Fighter fighter, int timeSpent);
}
