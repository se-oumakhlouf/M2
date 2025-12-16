package fr.uge.dragonball.statistics;

public sealed interface Fighter permits BasicFighter, TransformedFighter, FusedFighter {
  String name();
  int power();
  int maxHealth();
}
