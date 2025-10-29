package fr.uge.poo.visitors.expr;

import fr.uge.poo.visitors.expr.Expr.BinOp;
import fr.uge.poo.visitors.expr.Expr.Value;

public interface ExprVisitor<T> {
  T visitValue(Value value);
  T visitBinOp(BinOp binOp);
}
