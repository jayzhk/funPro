package reductions

import org.scalameter._

object ParallelParenthesesBalancingRunner {

  @volatile var seqResult = false

  @volatile var parResult = false

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 40,
    Key.exec.maxWarmupRuns -> 80,
    Key.exec.benchRuns -> 120,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def main(args: Array[String]): Unit = {
    val length = 100000000
    //val length = 100000
    val chars = new Array[Char](length)
    val threshold = 10000
    //val threshold = 1000
    val seqtime = standardConfig measure {
      seqResult = ParallelParenthesesBalancing.balance(chars)
    }
    println(s"sequential result = $seqResult")
    println(s"sequential balancing time: $seqtime ms")

    val fjtime = standardConfig measure {
      parResult = ParallelParenthesesBalancing.parBalance(chars, threshold)
    }
    println(s"parallel result = $parResult")
    println(s"parallel balancing time: $fjtime ms")
    println(s"speedup: ${seqtime / fjtime}")
  }
}

object ParallelParenthesesBalancing {

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def balance(chars: Array[Char]): Boolean = {

    def balanced(xs: Array[Char], open: Int): Boolean = {
      if(xs.isEmpty) open == 0
      else if(xs.head == '(') balanced(xs.tail, open + 1)
      else if(xs.head == ')') open > 0 && balanced(xs.tail, open -1)
      else balanced(xs.tail, open)
    }
    balanced(chars, 0)
  }

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def parBalance(chars: Array[Char], threshold: Int): Boolean = {

    def traverse(idx: Int, until: Int, arg1: Int, arg2: Int)  : (Int, Int) = {
      var i = idx ;
      var (open, close) = (arg1, arg2)
      while(i < until){
         chars(i) match  {
           case '(' => open = open + 1
           case ')' => if(open > 0) open = open -1 else close = close + 1
           case _ =>
         }
        i = i + 1
      }

      (open, close)


//      if(idx == until)  (arg1 - arg2)
//      else if(chars(idx) == '(') traverse(idx + 1, until, arg1 + 1, arg2)
//      else if(chars(idx) == ')') traverse(idx + 1, until, arg1, arg2 + 1)
//      else traverse(idx + 1, until, arg1, arg2)
    }

    def reduce(from: Int, until: Int) : (Int, Int) = {

      if(until - from < threshold) {
        traverse(from, until, 0, 0)
      }else {
        val mid = from + (until - from) / 2
        val (l, r) = common.parallel(reduce(from, mid), reduce(mid, until))
        (l._1 - r._2, l._2 + r._1)
      }
    }
    reduce(0, chars.length) == (0, 0)
  }

  // For those who want more:
  // Prove that your reduction operator is associative!


}
