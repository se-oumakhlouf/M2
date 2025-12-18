package fr.uge.poo.ex1;

import java.util.List;

public interface RobotLogger {
  void takeDamage(int damage);
  void chooseTarget(List<Robot> targets);
}
