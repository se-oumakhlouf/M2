package fr.uge.poo.ex2.decorator;

import fr.uge.poo.ex2.Robot;

import java.util.List;

public interface RobotLogger {
  void takeDamage(int damage);
  void chooseTarget(List<Robot> targets);
}
