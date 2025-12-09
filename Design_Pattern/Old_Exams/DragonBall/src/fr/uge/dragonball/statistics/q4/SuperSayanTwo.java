package fr.uge.dragonball.statistics.q4;

public final class SuperSayanTwo implements Transformation {

	@Override
	public Fighter transform(FighterInfo fighter) {
		return Fighter.with()
				.name(fighter.name())
				.power(fighter.power() * 4)
				.maxHealth(fighter.maxHealth() / 4)
				.build();			
	}

}
