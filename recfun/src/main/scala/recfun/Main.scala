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
    def pascal(c: Int, r: Int): Int =
    if (c==0 || c==r) 1 else (pascal(c-1, r-1) + pascal(c,r-1))

//    println(pascal(1,2))
  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {
        def helperF(chars: List[Char], openBrackets:Int): Boolean = {
            if (chars.isEmpty) {
              openBrackets==0
            } else {
              val first = chars.head
              val n =
                if (first == '(') openBrackets + 1
                else if (first == ')') openBrackets - 1
                else openBrackets
              if (n >= 0) helperF(chars.tail, n)
              else false
            }
        }

        helperF(chars,0)

  }


  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = {


               if (money==0) 1
               else if (money < 0 || coins.isEmpty) 0
               else countChange(money, coins.tail) + countChange(money-coins.head, coins)



  }

  }
