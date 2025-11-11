package fr.uge.poo.visitors.expr;

import fr.uge.poo.visitors.expr.Expr.BinOp;
import fr.uge.poo.visitors.expr.Expr.Value;

public class ToStringVisitor implements ExprVisitor<String> {

  private final StringBuilder builder;

  public ToStringVisitor(StringBuilder builder) {
    this.builder = builder;
  }

  @Override
  public String visitValue (Value value) {
    builder.append(value.value());
    return "";
  }

  @Override
  public String visitBinOp (BinOp binOp) {
    builder.append("(")
      .append(binOp.left().accept(this))
      .append(" ")
      .append(binOp.symbol())
      .append(" ")
      .append(binOp.right().accept(this))
      .append(")");
    return "";
  }

  @Override
  public String toString() {
    return builder.toString();
  }
}
