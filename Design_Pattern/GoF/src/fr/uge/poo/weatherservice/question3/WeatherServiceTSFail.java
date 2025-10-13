package fr.uge.poo.weatherservice.question3;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import com.evilcorp.weatherservice.WeatherService;

/* This class is thread-safe */
public class WeatherServiceTSFail implements WeatherService {
	
	private static Optional<WeatherService> instance = Optional.empty();

	private WeatherServiceTSFail() {
		if (ThreadLocalRandom.current().nextBoolean()) {
			throw new IllegalStateException("Connection failed please try again later");
		}
		System.out.println("Creating a connection to WeatherServiceTSFail");
	}

	public static Optional<WeatherService> getInstance() {
		if (instance.isEmpty()) {
			try {
				instance = Optional.of(new WeatherServiceTSFail());
			} catch (IllegalStateException e) {
				System.out.println("Service not available: " + e.getMessage());
				instance = Optional.empty();
			}
		}
		return instance;
	}

	public int query(String city) {
		return city.hashCode() % 30;
	}
}
