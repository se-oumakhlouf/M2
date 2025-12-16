package fr.uge.dragonball.statistics.q4;

public sealed interface Fighter permits BasicFighter, TransformedFighter, FusedFighter {
  String name();
  int power();
  int maxHealth();
}
