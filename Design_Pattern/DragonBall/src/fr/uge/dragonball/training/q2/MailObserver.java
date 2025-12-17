package fr.uge.dragonball.training.q2;

public class MailObserver implements TrainingRoomObserver {
  @Override
  public void over9000(TrainingRoom trainingRoom) {
    System.out.println("""
        [Mail] sent to {arnarud.carayol@univ-eiffel.fr} -> Message : {"It's over 9000"}""");
  }
}
