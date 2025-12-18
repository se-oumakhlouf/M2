package fr.uge.poo.ex2.observer;

import fr.uge.poo.ex2.Robot;

import java.util.List;

public class MailObserver implements FightObserver {

  @Override
  public void onFightStart(List<Robot> participants) {
    System.out.println("[Mail] -> Robots registered for the fight :\n\t" + participants.stream().map(Robot::name).toList());
  }
}
