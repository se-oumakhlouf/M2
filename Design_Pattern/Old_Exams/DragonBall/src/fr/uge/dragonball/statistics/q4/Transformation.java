package fr.uge.dragonball.statistics.q4;

public sealed interface Transformation permits SuperSayan, SuperSayanTwo, SuperBlue {

	public Fighter transform(FighterInfo fighter);
}
