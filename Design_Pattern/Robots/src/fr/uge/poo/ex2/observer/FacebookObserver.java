package fr.uge.poo.ex2.observer;

import fr.uge.poo.ex2.Robot;

public class FacebookObserver implements FightObserver {

  @Override
  public void onFightEnd(Robot winner) {
    StringBuilder postBuilder = new StringBuilder();
    postBuilder.append("[Facebook] -> ")
        .append("Fight ended !\n");

    if (winner != null) {
      postBuilder.append("\tWinner: ").append(winner.name()).append("\n");
    }

    System.out.println(postBuilder);
  }
}
