package fr.uge.dragonball.training.q2;

public class InstagramObserver implements TrainingRoomObserver {
  @Override
  public void fighterLeft(TrainingRoom trainingRoom, TrainingRoom.Fighter fighter) {
    System.out.println("[Instagram POST] : {" + fighter + "} has left the training room");
    System.out.println("\t Fighters that left before him : " + trainingRoom.exitedFighters());
  }
}
