package fr.uge.dragonball.locator;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class RedArmyLocator {

  /**
   * The {@code RedArmyLocator} class is responsible for locating Dragon Balls on a Mercator-based map.
   * The Mercator coordinate (x,y) are between -1000 and 1000. They can be converted to latitude and longitude
   * by the formulas : latitude = ((x+y)+2000)/4000*360
   * and longitude = (x+1000)/2000*360
   */

  public enum Continent {
    ASIA,
    AFRICA,
    NORTH_AMERICA,
    SOUTH_AMERICA,
    ANTARCTICA,
    EUROPE,
    AUSTRALIA
  }

  /**
   * Locates the Mercator X-coordinate of a Dragon Ball for a specific continent.
   *
   * @param dragonballId The unique identifier of the Dragon Ball (1 to 7).
   * @param continent    The continent where the Dragon Ball is being searched.
   */

  public Double locateX(int dragonballId,Continent continent) throws InterruptedException {
    if (dragonballId != continent.ordinal()){
      return null;
    }
    Thread.sleep(10L*dragonballId);
    // 1/10 chance the Dragon Ball is not located
    if (ThreadLocalRandom.current().nextInt(10) == 0) {
      return null;
    }
    return  ThreadLocalRandom.current().nextDouble(-1000, 1000); // Mercator X : -1000 to 1000
  }

  /**
   * Locates the Mercator X-coordinate of a Dragon Ball for a specific continent.
   *
   * @param dragonballId The unique identifier of the Dragon Ball (1 to 7).
   * @param continent    The continent where the Dragon Ball is being searched.
   */

  public Double locateY(int dragonballId,Continent continent) throws InterruptedException {
    if (dragonballId != continent.ordinal()){
      return null;
    }
    Thread.sleep(10L*dragonballId);
    // 1/10 chance the Dragon Ball is not located
    if (ThreadLocalRandom.current().nextInt(10) == 0) {
      return null;
    }
    return  ThreadLocalRandom.current().nextDouble(-1000, 1000); // Mercator Y : -1000 to 1000
  }


  /**
   * Locates all Dragon Balls collectively using a faster approach compared to individual lookups. If you need only one
   * location this approach is slower.
   *
   * @return A list of Mercator coordinates (each represented as an array of size 2) for the Dragon Balls.
   * Each array contains the X-coordinate at index 0 and the Y-coordinate at index 1. If a Dragon Ball cannot
   * be located, its corresponding entry in the list will be {@code null}.
   */

  public List<double[]> fastLocateAll() throws InterruptedException {
    Thread.sleep(100L);
    return IntStream.range(1, 8).mapToObj(i -> {
      if (ThreadLocalRandom.current().nextInt(10) == 0) {
        return null;
      }
      var array = new double[2];
      array[0] = ThreadLocalRandom.current().nextDouble(-1000, 1000); // Mercator X : -1000 to 1000
      array[1] = ThreadLocalRandom.current().nextDouble(-1000, 1000); // Mercator X : -1000 to 1000
      return array;
    }).toList();
  }
}