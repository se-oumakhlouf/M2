package fr.uge.dragonball.training.q2;

public interface TrainingRoomObserver {
  default void over9000(TrainingRoom trainingRoom) {};
  default void fighterLeft(TrainingRoom trainingRoom, TrainingRoom.Fighter fighter) {};
  default void newBestFighter(TrainingRoom trainingRoom, TrainingRoom.Fighter fighter) {};
}
