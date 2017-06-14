package observatory

import java.io.{BufferedReader, InputStreamReader}
import java.time.LocalDate

import scala.io.Source

/**
  * 1st milestone: data extraction
  */
object Extraction {

  //@transient lazy val conf: SparkConf = new SparkConf().setMaster("local").setAppName("StackOverflow")
  //@transient lazy val sc: SparkContext = new SparkContext(conf)

  /**
    * @param year             Year number
    * @param stationsFile     Path of the stations resource file to use (e.g. "/stations.csv")
    * @param temperaturesFile Path of the temperatures resource file to use (e.g. "/1975.csv")
    * @return A sequence containing triplets (date, location, temperature)
    */
  def locateTemperatures(year: Int, stationsFile: String, temperaturesFile: String): Iterable[(LocalDate, Location, Double)] = {

    val stations = Source.fromFile(stationsFile).getLines().map(line => line.split(",")).filter( s => s(2) != "" && s(3) != "")
    val stationPairs = stations.map(line => (Key(line(0), line(1)), Location(line(2).toDouble, line(3).toDouble))).toMap
    val temperatures = Source.fromFile(temperaturesFile).getLines().map(line => line.split(","));
    val temperaturePair = temperatures.map(line => (Key(line(0), line(1)), LocalDate.of(year, line(2).toInt, line(3).toInt), line(4).toDouble))
    temperaturePair.map(temp => {
      val location = stationPairs.get(temp._1)
      (temp._2, location, (temp._3 - 32)* (5 / 9))
    })




  }

  /**
    * @param records A sequence containing triplets (date, location, temperature)
    * @return A sequence containing, for each location, the average temperature over the year.
    */
  def locationYearlyAverageRecords(records: Iterable[(LocalDate, Location, Double)]): Iterable[(Location, Double)] = {
    ???
  }
}

case class Station(stnId: Int, wbanId:)
