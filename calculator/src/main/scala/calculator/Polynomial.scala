package calculator

object Polynomial {
  def computeDelta(a: Signal[Double], b: Signal[Double],
      c: Signal[Double]): Signal[Double] = {
    Signal(b() * b() - 4 * a() * c())
  }

  def computeSolutions(a: Signal[Double], b: Signal[Double],
      c: Signal[Double], delta: Signal[Double]): Signal[Set[Double]] = {
    // -b +- sqrt(delta) / 2a
    Signal {
      delta() match {
        case delta if (delta < 0) => Set.empty
        case delta if (delta == 0) => Set(-b())
        case delta if (delta > 0) => {val v1 = (-b() + math.sqrt(delta)) / (2 * a()); val v2 = (-b() -  math.sqrt(delta))/ (2 * a()); Set(v1, v2)}
      }
    }
  }
}
