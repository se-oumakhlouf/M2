package fr.uge.poo.weatherservice.question1;

import com.evilcorp.weatherservice.WeatherService;

/* This class is thread-safe */
public class WeatherServiceTS implements WeatherService {

	private WeatherServiceTS() {
		System.out.println("Creating a connection to WeatherServiceTS");
	}

	public int query(String city) {
		return city.hashCode() % 30;
	}
	
	private static final WeatherServiceTS INSTANCE = new WeatherServiceTS();
	
	public static WeatherServiceTS getInstance() {
		return INSTANCE;
	}
}
