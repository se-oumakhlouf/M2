package fr.uge.dragonball.training.q2;

public class FacebookObserver implements TrainingRoomObserver {
  @Override
  public void newBestFighter(TrainingRoom trainingRoom, TrainingRoom.Fighter fighter) {
    System.out.println("[Facebook POST] : {" + fighter + "} is the new best fighter");
  }
}
