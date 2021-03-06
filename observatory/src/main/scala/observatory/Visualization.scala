package observatory

import com.sksamuel.scrimage.{Image, Pixel}

import scala.math._

/**
  * 2nd milestone: basic visualization
  */
object Visualization {

  val radius = 6371
  val power = 2
  val IMAGE_WIDTH = 360
  val IMAGE_HEIGHT = 180

  /**
    * @param temperatures Known temperatures: pairs containing a location and the temperature at this location
    * @param location Location where to predict the temperature
    * @return The predicted temperature at `location`
    */
  def predictTemperature(temperatures: Iterable[(Location, Double)], location: Location): Double = {

    val tempDistances = temperatures.map(p => (p._2, greatCycleDistance(p._1, location)))
    val matches = tempDistances.find(p => p._2 <= 1)
    if(matches.isDefined) matches.get._1
    else {
      val filtered = tempDistances.filter(p => p._2 > 1).toMap
      val tempWeightsPairs = filtered.mapValues(1 / pow(_, power))

      tempWeightsPairs.map(p => p._1 * p._2).sum / tempWeightsPairs.values.sum
    }

  }

  def greatCycleDistance(p1 : Location, p2 : Location ) : Double  = {
    acos(sin(toRadians(p1.lat)) * sin(toRadians(p2.lat)) +
      cos(toRadians(p1.lat)) * cos(toRadians(p2.lat)) * cos(toRadians(abs(p1.lon - p2.lon)))) * radius
  }

  /**
    * @param points Pairs containing a value and its associated color
    * @param value The value to interpolate
    * @return The color that corresponds to `value`, according to the color scale defined by `points`
    */
  def interpolateColor(points: Iterable[(Double, Color)], value: Double): Color = {
    val sorted = points.toList.sortWith((a, b) => a._1 < b._1)
    if(sorted.head._1 >= value ) sorted.head._2
    else if (sorted.last._1 <= value) sorted.last._2
    else {
      val matches = sorted.find(_._1 == value)
      if(matches.isDefined) matches.get._2
      else {
        val upper = sorted.indexWhere(p => p._1 > value)
        val lower = upper - 1
        linearInterpolate(sorted(lower), sorted(upper), value)
      }
    }
  }

  def linearInterpolate( lower: (Double, Color), upper: (Double, Color), x: Double) : Color = {
    val (r, g, b) = upper._2 - lower._2
    val deltaTemp = upper._1 - lower._1
    val c = x - lower._1
    val (r1, g1, b1 ) = (
      lower._2.red +  r * (c / deltaTemp),
      lower._2.green + g * (c / deltaTemp),
      lower._2.blue +  b * (c  / deltaTemp)
    )

    Color(math.round(r1).toInt, math.round(g1).toInt, math.round(b1).toInt)
  }


  /**
    * @param temperatures Known temperatures
    * @param colors Color scale
    * @return A 360×180 image where each pixel shows the predicted temperature at its location
    */
  def visualize(temperatures: Iterable[(Location, Double)], colors: Iterable[(Double, Color)]): Image = {

    val locations = for {
      lan <- 90 until -90 by -1
      lon <- -180 until 180
    } yield Location(lan, lon)

  //  println(s"converted locations = $locations")

    val predicts = locations.map(p => predictTemperature(temperatures, p))

   // println(s"predicted temperatures = $predicts")

    val predictedColors = predicts.map(p => interpolateColor(colors, p))
    val pixelArray = predictedColors.map(p =>Pixel(p.red, p.green, p.blue, 127)).toArray
    Image(IMAGE_WIDTH, IMAGE_HEIGHT, pixelArray)

  }

  def convert(loc: Location) : (Int, Int) = {
   // println(loc)
    ((math.round(loc.lat) + 90).toInt, (math.round(loc.lon) + 180).toInt)
   // println(pair)
   // pair
  }

}

