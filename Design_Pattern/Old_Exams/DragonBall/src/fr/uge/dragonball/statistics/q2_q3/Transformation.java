package fr.uge.dragonball.statistics.q2_q3;

public sealed interface Transformation permits SuperSayan, SuperSayanTwo, SuperBlue {

	public Fighter transform(FighterInfo fighter);
}
