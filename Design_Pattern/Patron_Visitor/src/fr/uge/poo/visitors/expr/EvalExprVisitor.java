package fr.uge.poo.visitors.expr;

import fr.uge.poo.visitors.expr.Expr.BinOp;
import fr.uge.poo.visitors.expr.Expr.Value;

public class EvalExprVisitor implements ExprVisitor<Integer> {

  @Override
  public Integer visitValue (Value value) {
    return value.value();
  }

  @Override
  public Integer visitBinOp (BinOp binOp) {
    return binOp.operator().applyAsInt(binOp.left().accept(this), binOp.right().accept(this));
  }

}
