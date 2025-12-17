package fr.uge.dragonball.locator.q1;

import java.util.AbstractList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * BulmaLocator is responsible for locating the 7 Dragon Balls.
 * Each Dragon Ball has a random geographical position represented by latitude and longitude.
 * There is a 1/10 chance that a Dragon Ball cannot be located.
 */
// Desgin Pattern : Proxy
public class BulmaLocator {

  /**
   * Retrieves the positions of the 7 Dragon Balls.
   *
   * @return a list of positions where each position is a list containing latitude and longitude.
   * If a Dragon Ball is not located, the corresponding position will be null.
   */
  public List<List<Integer>> locateAll() throws InterruptedException {
    return new AbstractList<>() {
      @Override
      public List<Integer> get(int index) {
        Objects.checkIndex(index, 8);
        try {
          return locate(index + 1);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          throw new RuntimeException(e);
        }
      }

      @Override
      public int size() {
        return 7;
      }
    };
  }

  /**
   * Generates a random geographical position for a Dragon Ball.
   *
   * @return a list containing latitude and longitude, or null if the Dragon Ball is not located.
   */
  private List<Integer> locate(int dragonballId) throws InterruptedException {
    Thread.sleep(100L * dragonballId);
    // 1/10 chance the Dragon Ball is not located
    if (ThreadLocalRandom.current().nextInt(10) == 0) {
      return null;
    }
    int latitude = ThreadLocalRandom.current().nextInt(-90, 91); // Latitude range: -90 to 90
    int longitude = ThreadLocalRandom.current().nextInt(-180, 181); // Longitude range: -180 to 180
    return List.of(latitude, longitude);
  }

  /**
   * Main method to execute the BulmaLocator and display the positions of the Dragon Balls.
   *
   * @param args command-line arguments (not used).
   */
  public static void main(String[] args) throws InterruptedException {
    BulmaLocator locator = new BulmaLocator();
    List<List<Integer>> dragonBallPositions = locator.locateAll();

    // Print the positions of the Dragon Balls
    for (int i = 0; i < 6; i++) {
      List<Integer> position = dragonBallPositions.get(i);
      if (position == null) {
        System.out.println("Dragon Ball " + (i + 1) + ": Not located");
      } else {
        System.out.println("Dragon Ball " + (i + 1) + ": Latitude = " + position.get(0) + ", Longitude = " + position.get(1));
      }
    }
  }
}