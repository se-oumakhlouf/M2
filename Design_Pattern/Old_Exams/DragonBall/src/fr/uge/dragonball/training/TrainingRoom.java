package fr.uge.dragonball.training;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class TrainingRoom {

    private final HashMap<Fighter,Integer> timeSpent = new HashMap<>();

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
            if (days >= 10){
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
        TrainingRoom room = new TrainingRoom();

        // Create two fighters
        TrainingRoom.Fighter goku = new TrainingRoom.Fighter("Goku", 9000, 100);
        TrainingRoom.Fighter vegeta = new TrainingRoom.Fighter("Vegeta", 8500, 100);

        // Add fighters to the training room
        room.addFighter(goku);
        room.addFighter(vegeta);

        // Simulate 10 days
        System.out.println("Simulation starts: Training Room activities");
        for (int day = 1; day <= 10; day++) {
            System.out.println("Day " + day + ":");
            room.newDay();
            System.out.println(room);
        }


    }
}
