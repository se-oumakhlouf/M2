package fr.uge.poo.ex2.observer;

import fr.uge.poo.ex2.Robot;

import java.util.List;

public interface FightObserver {

  default void onFightStart(List<Robot> participants) {}
  default void onFightEnd(Robot winner) {}
  default void onRoundEnd(int round, List<Robot> deadRobots) {}
}
