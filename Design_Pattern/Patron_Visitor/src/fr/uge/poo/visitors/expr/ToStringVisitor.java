package fr.uge.poo.visitors.expr;

import fr.uge.poo.visitors.expr.Expr.BinOp;
import fr.uge.poo.visitors.expr.Expr.Value;

public class ToStringVisitor implements ExprVisitor<String> {

  private final StringBuilder builder = new StringBuilder();

  @Override
  public String visitValue (Value value) {
    return builder.append("(")
        .append(value.value())
        .append(")")
        .toString();
  }

  @Override
  public String visitBinOp (BinOp binOp) {
    return builder.append("(")
        .append(binOp.left().accept(this))
        .append(" ")
        .append(binOp.symbol())
        .append(" ")
        .append(binOp.right().accept(this))
        .append(")")
        .toString();
//    return "(" + binOp.left().accept(this) + " " + binOp.symbol() + " " + binOp.right().accept(this) + ")";
  }
}
