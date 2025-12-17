package fr.uge.dragonball.locator.q2;

import fr.uge.dragonball.locator.RedArmyLocator;

import java.util.List;

public class RedArmyLocatorAdapter implements LocatorAPI {

  private final RedArmyLocator locator = new RedArmyLocator();

  @Override
  public List<Point> locateAll() {
    try {
      return locator.fastLocateAll().stream().map(this::mercatorToPoint).toList();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return List.of();
    }
  }

  @Override
  public Point locate(int id) {
    try {
      double lat = locator.locateX(id, RedArmyLocator.Continent.EUROPE);
      double lon = locator.locateY(id, RedArmyLocator.Continent.EUROPE);
      return new Point(lat, lon);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return null;
    }
  }

  private Point mercatorToPoint(double[] mercator) {
    if (mercator == null) return null;
    double x = mercator[0];
    double y = mercator[1];

    double lat = ((x + y) + 2000) / 4000 * 360;
    double lon = (x + 1000) / 2000 * 360;

    return new Point(lat, lon);
  }
}
