package fr.uge.dragonball.statistics.q2_q3;

import java.util.Objects;

public class Fighter {

	private final String name;
	private final int power;
	private final int maxHealth;
	
	private Fighter(String name, int power, int maxHealth) {
		this.name = Objects.requireNonNull(name);
		this.power = power;
		this.maxHealth = maxHealth;
	}
	
	public static FighterBuilder with() {
		return new FighterBuilder();
	}
	
	public static class FighterBuilder {
		private String name;
		private int power;
		private int maxHealth;
	
		public FighterBuilder name(String name) {
			this.name = Objects.requireNonNull(name);
			return this;
		}
		
		public FighterBuilder power(int power) {
			this.power = power;
			return this;
		}
		
		public FighterBuilder maxHealth(int maxHealth) {
			this.maxHealth = maxHealth;
			return this;
		}
		
		public Fighter build() {
			return new Fighter(name, power, maxHealth);
		}
		
	}
	
	public Fighter fusionWith(Fighter other) {
		String name = "<" + this.name + "-" + other.name + ">";
		int maxHealth = Integer.min(this.maxHealth, other.maxHealth);
		int power = this.power + other.power;
		
		return new Fighter(name, power, maxHealth);
	}
	
	public Fighter tranform(Transformation transformation) {
		return transformation.transform(new FighterInfo(this.name, this.power, this.maxHealth));
	}

	
	@Override
	public String toString() {
		return "Name: " + name + ", Power: " + power + " Max Health: " + maxHealth;
	}
	
}
