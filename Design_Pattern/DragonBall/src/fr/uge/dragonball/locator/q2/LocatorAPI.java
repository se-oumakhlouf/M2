package fr.uge.dragonball.locator.q2;

import java.util.List;

public interface LocatorAPI {

  record Point(double x, double y) {}

  List<Point> locateAll();
  Point locate(int id);
}
