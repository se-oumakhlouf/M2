package fr.uge.poo.ex2.observer;

import fr.uge.poo.ex2.Robot;

import java.util.List;
import java.util.Objects;

public class TwitterObserver implements FightObserver {

  @Override
  public void onRoundEnd(int round, List<Robot> deadRobots) {
    Objects.requireNonNull(deadRobots);
    StringBuilder postBuilder = new StringBuilder();
    postBuilder.append("[Twitter] -> Round ").append(round).append(" ended !\n\t");
    if (!deadRobots.isEmpty()) {
      postBuilder.append("Dead robots: ")
          .append(deadRobots.stream().map(Robot::name).toList());
    } else {
      postBuilder.append("No death in this round.");
    }

    System.out.println(postBuilder);
  }
}
