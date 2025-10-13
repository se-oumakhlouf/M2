package fr.uge.poo.weatherservice.question2;

import com.evilcorp.weatherservice.WeatherService;

public class WeatherServiceNTS implements WeatherService {

	private static final WeatherServiceNTS INSTANCE = new WeatherServiceNTS();
	private final Object lock = new Object();

	private WeatherServiceNTS() {
		synchronized (lock) {
			System.out.println("Creating a connection to WeatherServiceTS");
		}
	}

	public int query(String city) {
		synchronized (lock) {
			return city.hashCode() % 30;
		}
	}

	public static synchronized WeatherServiceNTS getInstance() {
		return INSTANCE;
	}
}
