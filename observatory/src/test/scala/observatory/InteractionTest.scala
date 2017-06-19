package observatory

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers

@RunWith(classOf[JUnitRunner])
class InteractionTest extends FunSuite with Checkers {

  test("test convert coordinator to lat and lon") {
    val result = Interaction.tileLocation(2, 3, 3 )
    println(result)
  }

}
