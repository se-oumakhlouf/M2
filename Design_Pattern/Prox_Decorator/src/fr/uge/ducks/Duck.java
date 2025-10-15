package fr.uge.ducks;

public interface Duck {

	void quack();

	default void quackManyTimes(int n) {};

}