package observatory

import com.sksamuel.scrimage.Image
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

    val tempDistancePairs = temperatures.map(p => (p._2, greatCyclePhi(p._1, location) * radius)).toMap
      //.filter(pair => pair._1 < 1)
    val weights = tempDistancePairs.mapValues( 1 / pow(_, power))

    weights.map(p => p._1 * p._2).sum / weights.values.sum

  }

  def greatCyclePhi(p1 : Location, p2 : Location ) : Double  = {
    acos(sin(p1.lat) * sin(p2.lat) + cos(p1.lat) * cos(p2.lat) * cos(abs(p1.lon - p2.lon)))
  }

  /**
    * @param points Pairs containing a value and its associated color
    * @param value The value to interpolate
    * @return The color that corresponds to `value`, according to the color scale defined by `points`
    */
  def interpolateColor(points: Iterable[(Double, Color)], value: Double): Color = {
    ???
  }

  /**
    * @param temperatures Known temperatures
    * @param colors Color scale
    * @return A 360×180 image where each pixel shows the predicted temperature at its location
    */
  def visualize(temperatures: Iterable[(Location, Double)], colors: Iterable[(Double, Color)]): Image = {
    ???
  }

}

