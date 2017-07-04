package observatory

import com.sksamuel.scrimage.{Image, Pixel}

import scala.math._
import observatory.Visualization._
/**
  * 3rd milestone: interactive visualization
  */
object Interaction {

  /**
    * @param zoom Zoom level
    * @param x X coordinate
    * @param y Y coordinate
    * @return The latitude and longitude of the top-left corner of the tile, as per http://wiki.openstreetmap.org/wiki/Slippy_map_tilenames
    */
  def tileLocation(zoom: Int, x: Int, y: Int): Location = {
    Location(toDegrees(atan(sinh(Pi * (1.0 - 2.0 * y.toDouble / (1<<zoom))))),
    x.toDouble / (1<<zoom) * 360.0 - 180.0)
  }

  def locationTile(zoom: Int, lat : Double, lon: Double) : (Int, Int) = {
    (((lon + 180.0) / 360.0 * (1 << zoom)).toInt,
    ((1 - log(tan(toRadians(lat)) + 1 / cos(toRadians(lat))) / Pi) / 2.0 * (1 << zoom)).toInt)
  }


  def convertCord(zoom : Int, loc : Location): (Int, Int) = {

    val converted_x = (128 / Pi) * ( 1 << zoom) * (toRadians(loc.lon) + Pi)
    val converted_y = (128 / Pi) * (1 << zoom) * (Pi - log(tan(Pi / 4 + toRadians(loc.lat) / 2 )))
    (round(converted_x).toInt, round(converted_y).toInt)
  }

  /**
    * @param temperatures Known temperatures
    * @param colors Color scale
    * @param zoom Zoom level
    * @param x X coordinate
    * @param y Y coordinate
    * @return A 256×256 image showing the contents of the tile defined by `x`, `y` and `zooms`
    */
  def tile(temperatures: Iterable[(Location, Double)], colors: Iterable[(Double, Color)], zoom: Int, x: Int, y: Int): Image = {

    val locations = for {
      b <- y * 256 until y * 256 + 256
      a <- x * 256 until x * 256 + 256
    } yield tileLocation(zoom + 8, a, b)

    val locationCordPairs = locations.map(loc => (convertCord(zoom, loc), loc))

      //locationCordPairs.take(500).foreach(println)
//    println(s"x = $x, y = $y First =${locations(0)} and last = ${locations(256 * 256 -1)}")
//    println(s"x = $x, y = $y cord First =  ${convertCord(zoom, locations(0))} and cord last = ${convertCord(zoom, locations(256 * 256 -1))}")

    val predictedTemperatures = locations.map(loc => predictTemperature(temperatures, loc) )

    val predictedColors = predictedTemperatures.map(temp => interpolateColor(colors, temp))

    val pixelArray = predictedColors.map(p =>Pixel(p.red, p.green, p.blue, 127)).toArray

   // println(s"pixelArray Length ${pixelArray.length}")

    Image(256, 256, pixelArray)

  }

  /**
    * Generates all the tiles for zoom levels 0 to 3 (included), for all the given years.
    * @param yearlyData Sequence of (year, data), where `data` is some data associated with
    *                   `year`. The type of `data` can be anything.
    * @param generateImage Function that generates an image given a year, a zoom level, the x and
    *                      y coordinates of the tile and the data to build the image from
    */
  def generateTiles[Data](
    yearlyData: Iterable[(Int, Data)],
    generateImage: (Int, Int, Int, Int, Data) => Unit
  ): Unit = {
    ???
  }

}
