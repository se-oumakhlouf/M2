package fr.uge.dragonball.training.q2;

import java.util.*;

// Design Pattern : Observer
public class TrainingRoom {

  private final HashMap<Fighter, Integer> timeSpent = new HashMap<>();
  private final ExitStrategy exitStrategy;
  private final Set<TrainingRoomObserver> observers = new HashSet<>();
  private final List<Fighter> exitedFighters = new LinkedList<>();

  public TrainingRoom(ExitStrategy exitStrategy) {
    this.exitStrategy = Objects.requireNonNull(exitStrategy);
  }

  public record Fighter(String name, int power, int maxHealth) {
  }

  /* Add a fighter to the training room */
  public void addFighter(Fighter fighter) {
    Objects.requireNonNull(fighter);

    boolean isStrongest = timeSpent.keySet().stream().allMatch(f -> f.power() < fighter.power());
    if (timeSpent.putIfAbsent(fighter, 0) != null) {
      throw new IllegalStateException();
    }
    if (isStrongest) {
      observers.forEach(o -> o.newBestFighter(this, fighter));
    }
  }

  /* Add a new day in the training room, the fighters that have spent 10 days
      leave the training room.
    */
  public void newDay() {
    var fightersToBeRemoved = new HashSet<Fighter>();
    for (var fighter : timeSpent.keySet()) {
      var days = timeSpent.merge(fighter, 1, Integer::sum);
      if (exitStrategy.shouldExit(fighter, days)) {
        fightersToBeRemoved.add(fighter);
      }
    }
    for (var fighter : fightersToBeRemoved) {
      removeFighter(fighter);
    }
    int accumulatedPower = timeSpent.keySet().stream().mapToInt(Fighter::power).sum();
    if (accumulatedPower > 9000) {
      observers.forEach(o -> o.over9000(this));
    }

  }

  /* Remove a fighter from the TrainingRoom */
  private void removeFighter(Fighter fighter) {
    observers.forEach(observer -> observer.fighterLeft(this, fighter));
    exitedFighters.add(fighter);
    timeSpent.remove(fighter);
  }

  public void register(TrainingRoomObserver observer) {
    observers.add(Objects.requireNonNull(observer));
  }

  public void unregister(TrainingRoomObserver observer) {
    observers.remove(Objects.requireNonNull(observer));
  }

  public List<Fighter> exitedFighters() {
    return Collections.unmodifiableList(exitedFighters);
  }

  @Override
  public String toString() {
    return "TrainingRoom{" +
               timeSpent +
               '}';
  }

  public static void main(String[] args) {
    // Create a training room
    TrainingRoom room100Days = new TrainingRoom((_, timeSpent) -> timeSpent > 100);
    TrainingRoom roomPower = new TrainingRoom((fighter, timeSpent) -> (fighter.power() / 100) < timeSpent);

    // Create two fighters
    Fighter goku = new TrainingRoom.Fighter("Goku", 9000, 100);
    Fighter vegeta = new TrainingRoom.Fighter("Vegeta", 8500, 100);
    Fighter gohanSSB = new TrainingRoom.Fighter("Gohan", 70_000, 1);
    Fighter trunks = new Fighter("Trunks", 6500, 100);


    // Add fighters to the training room
    room100Days.addFighter(goku);
    room100Days.addFighter(vegeta);
    room100Days.addFighter(trunks);
    room100Days.register(new MailObserver());
    room100Days.register(new InstagramObserver());
    room100Days.register(new FacebookObserver());

    roomPower.addFighter(goku);
    roomPower.addFighter(vegeta);

    // Simulate 100 days
    System.out.println("Simulation starts: Training Room activities");
    for (int day = 1; day <= 101; day++) {
      System.out.println("\nDay " + day + ":");
      if (day == 99) {
        room100Days.addFighter(gohanSSB);
      }
      room100Days.newDay();
      roomPower.newDay();
      System.out.println("Room 100 days : " + room100Days);
      System.out.println("Room Power : " + roomPower);
    }

  }
}