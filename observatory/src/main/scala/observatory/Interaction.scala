package observatory

import com.sksamuel.scrimage.Image

import scala.math._

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
    println(s"x = $x and y = $y and zoom = $zoom")
   // val location = Location(atan(sinh(Pi - (y / (1 << zoom)) * 2 * Pi )) * 180 / Pi , (x.toDouble / (1 << zoom)) * 360 - 180)
    val location = Location(toDegrees(atan(sinh(Pi * (1.0 - 2.0 * y.toDouble / (1<<zoom))))),
    x.toDouble / (1<<zoom) * 360.0 - 180.0)
    println(s" location: lan = ${location.lat} and lon = ${location.lon}")
    location
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

    val location = tileLocation(zoom, x, y)
    val converted_x = (128 / Pi) * ( 1 << zoom) * (toRadians(location.lon))
    val converted_y = 

//    val locations = for{
//      i <- x to x + 255
//      j <- y to y + 255
//    } yield { }
//
//    println(locations.take(50))
//
//    val predictedTemperatures = locations.map(loc => predictTemperature(temperatures, loc) )
//
//    val matches = predictedTemperatures.map(temp => interpolateColor(colors, temp))
//
//    val pixelArray = matches.map(p =>Pixel(p.red, p.green, p.blue, 127)).toArray
//
//    Image(256, 256, pixelArray)
    ???
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
