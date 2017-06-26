package observatory

import com.sksamuel.scrimage.{Image, Pixel}

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
    Location(atan(sinh(Pi - (y / pow(2, zoom)) * 2 * Pi )) * 180 / Pi , (x / pow(2.0, zoom)) * 360 - 180)
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
    val locations = for{
      i <- x to x + 255
      j <- y to y + 255
    } yield tileLocation(zoom, i, j)

    val matches = for{
       i <- 0 until locations.size
       temperature <- temperatures
      if(locations(i) == temperature._1)
    } yield (i, temperature._2)

    val pixArray = Array.fill[Pixel](256 * 256)(Pixel(255, 255, 255, 127))
    matches.foreach(p => {
      val color = Visualization.interpolateColor(colors, p._2)
      pixArray.update(p._1, Pixel(color.red, color.green, color.blue, 127))
      })
    Image(256, 256, pixArray)
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
