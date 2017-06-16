package observatory


import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers

@RunWith(classOf[JUnitRunner])
class VisualizationTest extends FunSuite with Checkers {

  test("great cycle distance") {
    val loc1 = Location(37.358,-78.438)
    val loc2 = Location(37.350,-78.433)
    val distance = Visualization.greatCycleDistance(loc1, loc2)
    println(distance)
  }

  test("test predicat temperature") {

    val result = Extraction.locateTemperatures(2015, "/testStation.csv", "/testTemperature.csv")
    val converted = Extraction.locationYearlyAverageRecords(result)

    val loc3 = Location(37.358,-78.450)
    //val loc2 = Location(37.350,-78.433)
    val temp = Visualization.predictTemperature(converted, loc3)
    println(temp)

  }

}
