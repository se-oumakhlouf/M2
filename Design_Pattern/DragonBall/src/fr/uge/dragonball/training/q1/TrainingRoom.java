package fr.uge.dragonball.training.q1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

// Design Pattern : Strategy
public class TrainingRoom {

  private final HashMap<Fighter,Integer> timeSpent = new HashMap<>();
  private final ExitStrategy exitStrategy;

  public TrainingRoom(ExitStrategy exitStrategy){
    this.exitStrategy = Objects.requireNonNull(exitStrategy);
  }

  public record Fighter(String name, int power, int maxHealth){};

  /* Add a fighter to the training room */
  public void addFighter(Fighter fighter){
    Objects.requireNonNull(fighter);
    if (timeSpent.putIfAbsent(fighter,0)!=null){
      throw new IllegalStateException();
    }
  }

  /* Add a new day in the training room, the fighters that have spent 10 days
      leave the training room.
    */
  public void newDay(){
    var fightersToBeRemoved = new HashSet<Fighter>();
    for (var fighter : timeSpent.keySet()) {
      var days = timeSpent.merge(fighter, 1, Integer::sum);
      if (exitStrategy.shouldExit(fighter, days)){
        fightersToBeRemoved.add(fighter);
      }
    }
    for(var fighter : fightersToBeRemoved){
      removeFighter(fighter);
    }

  }

  /* Remove a fighter from the TrainingRoom */
  private void removeFighter(Fighter fighter){
    timeSpent.remove(fighter);
  }

  @Override
  public String toString() {
    return "TrainingRoom{" +
               timeSpent +
               '}';
  }

  public static void main(String[] args) {
    // Create a training room
    TrainingRoom room100Days = new TrainingRoom((_, timeSpent) -> timeSpent < 100);
    TrainingRoom roomPower = new TrainingRoom((fighter, timeSpent) -> (fighter.power() / 100) < timeSpent);

    // Create two fighters
    Fighter goku = new Fighter("Goku", 9000, 100);
    Fighter vegeta = new Fighter("Vegeta", 8500, 100);


    // Add fighters to the training room
    room100Days.addFighter(goku);
    room100Days.addFighter(vegeta);
    roomPower.addFighter(goku);
    roomPower.addFighter(vegeta);

    // Simulate 10 days
    System.out.println("Simulation starts: Training Room activities");
    for (int day = 1; day <= 10; day++) {
      System.out.println("Day " + day + ":");
      room100Days.newDay();
      roomPower.newDay();
      System.out.println(room100Days);
    }

    // Simulate 100 days
    System.out.println("Simulation starts: Training Room activities");
    for (int day = 11; day <= 91; day++) {
      System.out.println("Day " + day + ":");
      roomPower.newDay();
      System.out.println(roomPower);
    }


  }
}