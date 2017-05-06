package calculator

sealed abstract class Expr

final case class Literal(v: Double) extends Expr

final case class Ref(name: String) extends Expr

final case class Plus(a: Expr, b: Expr) extends Expr

final case class Minus(a: Expr, b: Expr) extends Expr

final case class Times(a: Expr, b: Expr) extends Expr

final case class Divide(a: Expr, b: Expr) extends Expr

object Calculator {
  def computeValues(
                     namedExpressions: Map[String, Signal[Expr]]): Map[String, Signal[Double]] = {
    namedExpressions.map(x => (x._1, Signal(eval(x._2(), namedExpressions))))
  }

  def eval(expr: Expr, references: Map[String, Signal[Expr]]): Double = {

    def resolveValues(expr: Expr): Double = {
      expr match {
        case Literal(v) => v
        case Ref(name) => if(!references.exists(_._1 == name)) Double.NaN else references(name)() match {
          case Literal(v2) => v2
          case _ => Double.NaN
        }
        case _ => Double.NaN
      }
    }

    expr match {
      case Plus(a, b) => resolveValues(a) + resolveValues(b)
      case Minus(a, b) => resolveValues(a) - resolveValues(b)
      case Times(a, b) => resolveValues(a) * resolveValues(b)
      case Divide(a, b) => resolveValues(a) / resolveValues(b)
      case Literal(v) => v
      case Ref(name) => if(!references.exists(name == _._1)) Double.NaN else resolveValues(expr)
    }

  }

  /** Get the Expr for a referenced variables.
    * If the variable is not known, returns a literal NaN.
    */
  private def getReferenceExpr(name: String,
                               references: Map[String, Signal[Expr]]) = {
    references.get(name).fold[Expr] {
      Literal(Double.NaN)
    } { exprSignal =>
      exprSignal()
    }
  }
}
