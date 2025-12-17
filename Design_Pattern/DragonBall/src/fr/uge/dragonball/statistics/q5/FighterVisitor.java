package fr.uge.dragonball.statistics.q5;

public interface FighterVisitor<R, C> {
  R visit(BasicFighter fighter, C context);
  R visit(TransformedFighter fighter, C context);
  R visit(FusedFighter fighter, C context);
}
