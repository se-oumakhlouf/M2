package fr.uge.dragonball.statistics.q5;

public sealed interface Fighter permits BasicFighter, TransformedFighter, FusedFighter {
  String name();
  int power();
  int maxHealth();
  <R, C> R accept(FighterVisitor<R, C> visitor, C context);
}
