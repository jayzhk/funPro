package observatory

import com.sksamuel.scrimage.{Image, Pixel}

import scala.math._

/**
  * 2nd milestone: basic visualization
  */
object Visualization {

  val radius = 6371
  val power = 2

  /**
    * @param temperatures Known temperatures: pairs containing a location and the temperature at this location
    * @param location Location where to predict the temperature
    * @return The predicted temperature at `location`
    */
  def predictTemperature(temperatures: Iterable[(Location, Double)], location: Location): Double = {

     val matches = temperatures.find(p => p._1 == location)
    if(matches.isDefined) matches.get._2
    else {
      val tempDistancePairs = temperatures.map(p => (p._2, greatCycleDistance(p._1, location))).toMap
     // .filter(pair => pair._1 < 50)
      println(tempDistancePairs)
      val weights = tempDistancePairs.mapValues(1 / pow(_, power))
      weights.map(p => p._1 * p._2).sum / weights.values.sum
    }

  }

  def greatCycleDistance(p1 : Location, p2 : Location ) : Double  = {
    acos(sin(p1.lat) * sin(p2.lat) + cos(p1.lat) * cos(p2.lat) * cos(abs(p1.lon - p2.lon))) * radius
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
      val upper = sorted.indexWhere(p => p._1 > value)
      val lower = upper - 1
       linearInterpolate(sorted(lower), sorted(upper), value)
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

    // first to construct the pixels
    val colorCoordinatePairs  = temperatures.map(p => (convert(p._1), interpolateColor(colors, p._2)))
    val pixels = colorCoordinatePairs.map(p => (p._1._1 + p._1._1 * p._1._2, Pixel(p._2.red, p._2.green, p._2.blue, 255)))

    val pixelArray = Array.fill[Pixel](360 * 180)(Pixel(0, 255, 255, 255))

    pixels.foreach(p => pixelArray.update(p._1, p._2))

    Image(360, 180, pixelArray)

  }

  def convert(loc: Location) : (Int, Int) = {
    ((loc.lat + 180).toInt, (loc.lon + 90).toInt)
  }

}

