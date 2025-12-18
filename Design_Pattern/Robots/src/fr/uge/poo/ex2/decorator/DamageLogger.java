package fr.uge.poo.ex2.decorator;

import fr.uge.poo.ex2.Robot;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

public class DamageLogger implements RobotLogger {

  private final Robot robot;
  private final Logger logger = Logger.getLogger(DamageLogger.class.getName());

  public DamageLogger(Robot robot) {
    this.robot = Objects.requireNonNull(robot);
  }

  @Override
  public void takeDamage(int damage) {
    robot.takeDamage(damage);
    logger.info(robot.name() + " took " + damage + " damages and has now " + robot.life() + " life points");
  }

  @Override
  public void chooseTarget(List<Robot> targets) {
    Optional<Robot> optRobot = robot.chooseTarget(targets);
    optRobot.ifPresent(r -> logger.info(robot.name() + " selected " + r.name()));
  }
}
