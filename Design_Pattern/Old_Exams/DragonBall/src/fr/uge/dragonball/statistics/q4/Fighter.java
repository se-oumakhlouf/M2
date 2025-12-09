package fr.uge.dragonball.statistics.q4;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Fighter {

	private final String name;
	private final int power;
	private final int maxHealth;
	private final List<Fighter> fighters;
	
	private Fighter(String name, int power, int maxHealth, List<Fighter> fighters) {
		this.name = Objects.requireNonNull(name);
		this.power = power;
		this.maxHealth = maxHealth;
		this.fighters = Objects.requireNonNull(fighters);
	}
	
	public static FighterBuilder with() {
		return new FighterBuilder();
	}
	
	public static class FighterBuilder {
		private String name;
		private int power;
		private int maxHealth;
		private List<Fighter> fighters = new ArrayList<>();
	
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
		
		public FighterBuilder figher(Fighter fighter) {
			this.fighters.add(fighter);
			return this;
		}
		
		public Fighter build() {
			return new Fighter(name, power, maxHealth, fighters);
		}
		
	}
	
	public Fighter fusionWith(Fighter other) {
		String name = "<" + this.name + "-" + other.name + ">";
		int maxHealth = Integer.min(this.maxHealth, other.maxHealth);
		int power = this.power + other.power;
		List<Fighter> fighters = new ArrayList<>(this.fighters);
		fighters.addAll(other.fighters);
		fighters.add(this);
		fighters.add(other);
		
		return new Fighter(name, power, maxHealth, fighters);
	}
	
	public FighterInfo info() {
		return new FighterInfo(this.name, this.power, this.maxHealth, this.fighters);
	}
	
	public Fighter tranform(Transformation transformation) {
		return transformation.transform(info());
	}

	
	@Override
	public String toString() {
		return "Name: " + name + ", Power: " + power + " Max Health: " + maxHealth;
	}
	
}
