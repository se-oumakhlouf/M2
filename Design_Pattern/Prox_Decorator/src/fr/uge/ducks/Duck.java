package fr.uge.ducks;

public interface Duck {

	void quack();

	default void quackManyTimes(int n) {
        for (int i = 0; i < n; i++) {
            quack();
        }
    };

}