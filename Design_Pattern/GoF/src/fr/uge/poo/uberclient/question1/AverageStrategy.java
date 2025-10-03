package fr.uge.poo.uberclient.question1;

import java.util.List;
import java.util.OptionalDouble;

@FunctionalInterface
public interface AverageStrategy {
	
	AverageStrategy STANDARD = l -> l.stream().mapToInt(i -> i).average();
	
	OptionalDouble average(List<Integer> grades);
}
