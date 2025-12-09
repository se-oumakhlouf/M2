package fr.uge.dragonball.statistics.q2_q3;

public final class SuperSayan implements Transformation {

	@Override
	public Fighter transform(FighterInfo fighter) {
		return Fighter.with()
				.name(fighter.name())
				.power(fighter.power() * 2)
				.maxHealth(fighter.maxHealth() / 2)
				.build();				
	}
	

}
