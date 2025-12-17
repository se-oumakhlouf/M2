package fr.uge.dragonball.locator.q2;

import fr.uge.dragonball.locator.BulmaLocator;

import java.util.List;

public class BulmaLocatorAdapter implements LocatorAPI {

  private final BulmaLocator locator = new BulmaLocator();

  @Override
  public List<Point> locateAll() {
    try {
      return locator.locateAll().stream()
                 .map(coords -> {
                   if (coords == null) return null;
                   return new Point(coords.get(0), coords.get(1));
                 })
                 .toList();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return List.of();
    }
  }

  @Override
  public Point locate(int id) {
    return this.locateAll().get(id - 1);
  }

}
