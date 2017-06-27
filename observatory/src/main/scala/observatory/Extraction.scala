package observatory

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

    val stations = Source.fromInputStream(getClass.getResourceAsStream(stationsFile))
      .getLines().map(line => line.split(",")).filter( s => s.size > 3)
    val stationPairs = stations.map(line => (Key(line(0), line(1)), Location(line(2).toDouble, line(3).toDouble))).toMap
    val temperatures = Source.fromInputStream(getClass.getResourceAsStream(temperaturesFile)).getLines()
      .map(line => line.split(",")).filter(line => line.size > 2);
    val temperaturePairs = temperatures.map(line => (
        Key(line(0), line(1)),
        LocalDate.of(year, line(2).toDouble.toInt, line(3).toDouble.toInt),
        line(4).toDouble
      )
    )

    val combined = for {(key, date, temp) <- temperaturePairs
         loc = stationPairs.get(key)
         if (loc.isDefined)
         f = (temp - 32) * 5 /9
    } yield (date, loc.get, math.BigDecimal(f).setScale(1, BigDecimal.RoundingMode.HALF_UP).toDouble)

    combined.toSeq
  }

  /**
    * @param records A sequence containing triplets (date, location, temperature)
    * @return A sequence containing, for each location, the average temperature over the year.
    */
  def locationYearlyAverageRecords(records: Iterable[(LocalDate, Location, Double)]): Iterable[(Location, Double)] = {
    val groupedByYear = records.groupBy(k => k._1.getYear).values.head
    val groupedByLocation = groupedByYear.groupBy(k => k._2)
    val mapReduced = groupedByLocation.mapValues(p => (p.aggregate(0.0)((a, b) => a + b._3, (a, b) => a + b)) / p.size)
    mapReduced.map(p => (p._1, p._2))
  }
}