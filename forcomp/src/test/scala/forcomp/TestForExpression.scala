package forcomp

import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.FunSuite

/**
  * Created by huajiezeng on 4/7/17.
  */
@RunWith(classOf[JUnitRunner])
class TestForExpression extends  FunSuite{


  test("For 1 ~ 3 combination ") {

    val value : List[(Char, Int)] = List(('a',1), ('b',1), ('c',1))

    //value.

     val after = for{
         i<- 1 to value.length
         v1 = value.take(i)
         j <- 0 until value.length
         if(value(i) != value(j) && value(i)._1 != value(j)._1)
         v  = v1 :: List(value(j))
        }yield  v

    println(after)
  }

}
