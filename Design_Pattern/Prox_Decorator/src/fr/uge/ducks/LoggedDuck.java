package fr.uge.ducks;

import java.util.logging.Logger;

public class LoggedDuck implements Duck {

	private static final Logger logger = Logger.getLogger(LoggedDuck.class.getClass().getName());

	private final Duck duck;

	public LoggedDuck(Duck duck) {
		this.duck = duck;
	}

	@Override
	public void quack() {
		logger.info("Call to Quack!");
		duck.quack();
	}

	@Override
	public void quackManyTimes(int n) {
		duck.quackManyTimes(n);
	}
	
}
