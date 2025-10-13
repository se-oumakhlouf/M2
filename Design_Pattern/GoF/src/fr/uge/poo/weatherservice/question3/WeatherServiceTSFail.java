package fr.uge.poo.weatherservice.question3;

import java.util.concurrent.ThreadLocalRandom;

import com.evilcorp.weatherservice.WeatherService;

/* This class is thread-safe */
public class WeatherServiceTSFail implements WeatherService {
	
	private static final WeatherServiceTSFail INSTANCE = new WeatherServiceTSFail();

    private WeatherServiceTSFail() {
        if (ThreadLocalRandom.current().nextBoolean()){
            throw new IllegalStateException("Connection failed please try again later");
        }
        System.out.println("Creating a connection to WeatherServiceTSFail");
    }

    public int query(String city){
        return city.hashCode()%30;
    }
    
    public static WeatherServiceTSFail getInstance() {
    	return INSTANCE;
    }
}
