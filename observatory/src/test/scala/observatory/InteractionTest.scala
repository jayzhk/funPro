package observatory

import java.io.File

import observatory.Interaction._
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers

@RunWith(classOf[JUnitRunner])
class InteractionTest extends FunSuite with Checkers {


  lazy val points = Iterable(
    (60.0, Color(255, 255, 255)),
    (32.0, Color(255, 0, 0)),
    (12.0, Color(255, 255, 0)),
    (0.0, Color(0, 255, 255)),
    (-15.0, Color(0, 0, 255)),
    (-27.0, Color(255, 0, 255)),
    (-50.0, Color(33, 0, 107)),
    (-60.0, Color(0, 0, 0))
  )

  test("test convert coordinator to lat and lon") {

    println(scala.math.log(scala.math.E))

    val level0 = tileLocation(0, 0, 0 )
    println(level0)

    val level1 = List(
      tileLocation(1, 0, 0)
     ,tileLocation(1, 0, 1)
     ,tileLocation(1, 1, 0)
     ,tileLocation(1, 1, 1)
    )

    val level2 = List(
      tileLocation(2, 0, 0),
      tileLocation(2, 0, 1),
      tileLocation(2, 0, 2),
      tileLocation(2, 0, 3),
      tileLocation(2, 1, 0),
      tileLocation(2, 1, 1),
      tileLocation(2, 1, 2),
      tileLocation(2, 1, 3),
      tileLocation(2, 2, 0),
      tileLocation(2, 2, 1),
      tileLocation(2, 2, 2),
      tileLocation(2, 2, 3),
      tileLocation(2, 3, 0),
      tileLocation(2, 3, 1),
      tileLocation(2, 3, 2),
      tileLocation(2, 3, 3)
    )

    level1.foreach(println(_))
    level1.foreach( p => println(locationTile(1, p.lat, p.lon )))


    level2.foreach(println(_))
    level2.foreach( p => println(Interaction.covertToCord(2, p)))
    level2.foreach(p => println(locationTile(2, p.lat, p.lon)))


  }



  test("generate tiles of 256 * 256") {
    val result = Extraction.locateTemperatures(2017, "/testStation.csv", "/2017.csv")
    val converted = Extraction.locationYearlyAverageRecords(result)

    val image = Interaction.tile(converted, points, 0, 0, 0)

    val imageFile =  image.output(new File("myimage2.png"))
    //println(imageFile.getAbsolutePath)
  }
//
//  test("test converted x and y") {
//    val zoom = 1
//    val loc = tileLocation(2, 0, 1)
//    val converted_x = (128 / Pi) * ( 1 << zoom) * (toRadians(loc.lon))
//    val converted_y = (128 / Pi) * (1 << zoom) * (Pi - log1p(Pi / 4 + toRadians(loc.lat)))
//
//    println(s"x = $converted_x  and y = $converted_y")
//  }

}
