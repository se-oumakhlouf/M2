package fr.uge.poo.visitors.expr;

import fr.uge.poo.visitors.expr.Expr.BinOp;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.IntBinaryOperator;
import java.util.regex.Pattern;

public sealed interface Expr permits BinOp, Expr.Value {

  <T> T accept(ExprVisitor<T> visitor);

  record Value(int value) implements Expr {
    @Override
    public <T> T accept (ExprVisitor<T> visitor) {
      return  visitor.visitValue(this);
    }
  }

  record BinOp(Expr left, Expr right, String symbol, IntBinaryOperator operator) implements Expr {
    public BinOp {
      Objects.requireNonNull(left);
      Objects.requireNonNull(right);
      Objects.requireNonNull(symbol);
      Objects.requireNonNull(operator);
    }

    @Override
    public <T> T accept (ExprVisitor<T> visitor) {
      return visitor.visitBinOp(this);
    }
  }

  static Expr parse(Iterator<String> it) {
    if (!it.hasNext()) {
      throw new IllegalArgumentException("no more tokens");
    }
    String token = it.next();
    switch(token) {
      case "+":
        return new BinOp(parse(it), parse(it), token, Integer::sum);
      case "-":
        return new BinOp(parse(it), parse(it), token, (a, b) -> a - b);
      case "*":
        return new BinOp(parse(it), parse(it), token, (a, b) -> a * b);
      case "/":
        return new BinOp(parse(it), parse(it), token, (a, b) -> a / b);
      default:
        return new Value(Integer.parseInt(token));
    }
  }

  static void main(String[] args) {
    var iterator = Pattern.compile(" ").splitAsStream("+ * 4 + 1 1 + 2 3").iterator();
    var expr = Expr.parse(iterator);
    var visitor = new ToStringVisitor(new StringBuilder());
    expr.accept(visitor);
    System.out.println(visitor);
  }

}