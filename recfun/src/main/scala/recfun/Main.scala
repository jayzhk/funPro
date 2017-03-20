package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = {
      if(c == 0 || r == 0 || c == r) 1
      else pascal(c-1, r-1) + pascal(c, r-1)
    }
  
  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {

      def balanced(xs: List[Char], open: Int): Boolean = {
        if(xs.isEmpty) open == 0
        else if(xs.head == '(') balanced(xs.tail, open + 1)
        else if(xs.head == ')') open > 0 && balanced(xs.tail, open -1)
        else balanced(xs.tail, open)
      }
      balanced(chars, 0)

    }
  
  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = {
      if(money < 0 || coins.isEmpty) 0
      else if(money == 0) 1
      else countChange(money, coins.tail) + countChange((money - coins.head), coins)
    }
  }
