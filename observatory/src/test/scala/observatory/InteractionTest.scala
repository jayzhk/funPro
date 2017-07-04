package observatory

import java.io.File

import com.sksamuel.scrimage.{Image, Pixel}
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

  lazy val temperatures = Iterable(
    (Location(80.0, -160.0), -10.0),
    (Location(30.0, 30.0), 30.0),
    (Location(5, -80.0), 36.0),
    (Location(-10, 20), 20.0),
    (Location(-45, 90), 25.0),
    (Location(-55, 120), 10.0)
  )

  test("test convert coordinator to lat and lon") {

    //println(scala.math.log(scala.math.E))

    val level0 = tileLocation(0, 0, 0 )
   // println(level0)

    val level1 = List(
      tileLocation(1, 0, 0)
     ,tileLocation(1, 1, 0)
     ,tileLocation(1, 0, 1)
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
   // level1.foreach( p => println(locationTile(1, p.lat, p.lon )))


//    level2.foreach(println(_))
//    level2.foreach( p => println(convertCord(2, p)))
//    level2.foreach(p => println(locationTile(2, p.lat, p.lon)))

  }

//  test("tile location zoom = 8 ") {
//    val level1 = List(
//       tileLocation(1, 0, 0)
//      ,tileLocation(1, 0, 1)
//      ,tileLocation(1, 1, 0)
//      ,tileLocation(1, 1, 1)
//    )
//    level1.foreach(println(_))
//    level1.foreach( p => println(locationTile(1, p.lat, p.lon )))
//
//  }



  test("generate tiles of 256 * 256") {

//   val result = Extraction.locateTemperatures(1975, "/stations.csv", "/1975.csv")
//    // val result = Extraction.locateTemperatures(2017, "/testStation.csv", "/2017.csv")
//    val converted = Extraction.locationYearlyAverageRecords(result)





    val image0 = tile(temperatures, points, 0, 0, 0)
    val imageFile0 =  image0.output(new File("image0.png"))

    val image00 = tile(temperatures, points, 1, 0, 0)
    image00.output(new File("Image0-0.png"))

    val image10 = tile(temperatures, points, 1, 1, 0)
    image10.output(new File("Image1-0.png"))

    val image01 = tile(temperatures, points, 1, 0, 1)
    image01.output(new File("Image0-1.png"))

    val image11 = tile(temperatures, points, 1, 1, 1)
    image11.output(new File("Image1-1.png"))

    val combined = Image.filled(512, 512)

    def f(x:Int, y:Int, p: Pixel): Pixel = {


      if(x <= 255 && y <= 255) image00.pixel(x, y)
      else if (x >= 256 && y <= 255) image10.pixel(x - 256, y)
      else if (x <= 255 && y >= 256) image01.pixel(x, y - 256)
      else image11.pixel(x - 256, y - 256)
    }

    val image5 = combined.map(f)
    val imageFile1 =  image5.output(new File("Image512.png"))

    val image6 = image5.scaleTo(256, 256)
    val imageFile2 =  image6.output(new File("Image256.png"))
    
    //println(imageFile.getAbsolutePath)
  }








}
