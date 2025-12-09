package fr.uge.dragonball.statistics.q2_q3;

public final class SuperBlue implements Transformation {

	@Override
	public Fighter transform(FighterInfo fighter) {
		return Fighter.with()
				.name(fighter.name())
				.power(fighter.power() * fighter.maxHealth())
				.maxHealth(1)
				.build();			
	}

}
