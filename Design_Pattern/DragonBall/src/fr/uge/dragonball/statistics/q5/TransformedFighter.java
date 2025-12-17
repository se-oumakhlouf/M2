package fr.uge.dragonball.statistics.q5;

public record TransformedFighter(Transformation transformation, Fighter fighter) implements Fighter {

  @Override
  public String name() {
    return fighter.name() + " as " + transformation.name();
  }

  @Override
  public int power() {
    return transformation.power(fighter);
  }

  @Override
  public int maxHealth() {
    return transformation.maxHealth(fighter);
  }

  @Override
  public String toString() {
    return "Name: " + name() + ", Power: " + power() + ", MaxHealth: " + maxHealth();
  }

  @Override
  public <R, C> R accept(FighterVisitor<R, C> visitor, C context) {
    return visitor.visit(this, context);
  }
}
