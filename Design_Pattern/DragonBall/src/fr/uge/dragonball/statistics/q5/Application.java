package fr.uge.dragonball.statistics.q5;


import java.util.ArrayList;
import java.util.List;

// Design Pattern : Visitor
public class Application {

  public static List<BasicFighter> extractBasicFighters(Fighter fighter) {
    FighterVisitor<Void, List<BasicFighter>> visitor = new FighterVisitor<>() {
      @Override
      public Void visit(BasicFighter fighter, List<BasicFighter> context) {
        context.add(fighter);
        return null;
      }

      @Override
      public Void visit(TransformedFighter fighter, List<BasicFighter> context) {
        fighter.fighter().accept(this, context);
        return null;
      }

      @Override
      public Void visit(FusedFighter fighter, List<BasicFighter> context) {
        for (Fighter f : fighter.fighters()) {
          f.accept(this, context);
        }
        return null;
      }
    };

    List<BasicFighter> basicFighters = new ArrayList<>();
    fighter.accept(visitor, basicFighters);
    return List.copyOf(basicFighters);
  }

  public static void main(String[] args) {
    Fighter goku = new BasicFighter("Goku", 9_000, 10_000);
    Fighter vegeta = new BasicFighter("Vegeta", 8_500, 9_500);
    Fighter gohan = new BasicFighter("Gohan", 7_000, 3_500);
    Fighter trunks = new BasicFighter("Trunks", 6_500, 4_000);

    Transformation superSayan = new Transformation("Super Sayan", fighter -> fighter.power() * 2, fighter -> fighter.maxHealth() / 2);
    Transformation superSayanTwo = new Transformation("Super Sayan 2", fighter -> fighter.power() * 4, fighter -> fighter.maxHealth() / 4);
    Transformation superSayanBlue = new Transformation("Super Sayan Blue", fighter -> fighter.power() * fighter.maxHealth(), _ -> 1);

    try {
      new Transformation("Super Sayan", fighter -> fighter.power() * 10, fighter -> fighter.maxHealth() / 10);
    } catch (IllegalArgumentException e) {
      System.out.println("Tentative de doublon bloquée -> " + e.getMessage());
    }

    var megaFusion = new FusedFighter(List.of(goku, new TransformedFighter(superSayanTwo, vegeta), new TransformedFighter(superSayan, gohan), trunks));
    var megaFusionSS = new TransformedFighter(superSayanBlue, megaFusion);

    System.out.println(megaFusionSS);

    System.out.println("Basic fighters :");
    extractBasicFighters(megaFusionSS).forEach(System.out::println);
  }
}
