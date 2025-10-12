package fr.uge.poo.uberclient;

import java.util.List;
import java.util.OptionalDouble;

@FunctionalInterface
public interface AverageStrategy {
	
	AverageStrategy STANDARD = l -> l.stream().mapToInt(i -> i).average();
	
	static AverageStrategy overLast(int n) {
		if (n <= 0) {
			throw new IllegalStateException("At least one grade is needed");
		}
		return l -> l.stream().limit(n).mapToInt(i -> i).average();
	}
	
	OptionalDouble average(List<Integer> grades);
}
