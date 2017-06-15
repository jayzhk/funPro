package observatory

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ExtractionTest extends FunSuite {


  test("combine location and temperature") {
    val result = Extraction.locateTemperatures(2015, "/testStation.csv", "/testTemperature.csv")
    result.foreach(println)

  }

  test("test extraction for average per year") {

    val result = Extraction.locateTemperatures(2015, "/testStation.csv", "/testTemperature.csv")
    Extraction.locationYearlyAverageRecords(result)

  }

  
}