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
    val converted = Extraction.locationYearlyAverageRecords(result)
    converted.foreach(println)
  }

  test("Split line with empty value"){
    val line = "10010,,,,1"
    val splited = line.split(",")
    println(s" size = ${splited.size}")
    splited.foreach(println)

  }

  test("test extraction with sample data") {
    val result = Extraction.locateTemperatures(2015, "/stations.csv", "/1975.csv")
    val converted = Extraction.locationYearlyAverageRecords(result)
    converted.foreach(println)
  }

  
}