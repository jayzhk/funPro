package observatory

import java.io.File

import com.sksamuel.scrimage.Image
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

    //val result = Extraction.locateTemperatures(1975, "/stations.csv", "/1975.csv")
    val result = Extraction.locateTemperatures(2017, "/testStation.csv", "/2017.csv")
    val converted = Extraction.locationYearlyAverageRecords(result)

    val image0 = tile(converted, points, 0, 0, 0)
    val imageFile0 =  image0.output(new File("image0-0.png"))

    val image1 = tile(converted, points, 1, 0, 0)
    val image2 = tile(converted, points, 1, 1, 0)
    val image3 = tile(converted, points, 1, 0, 1)
    val image4 = tile(converted, points, 1, 1, 1)

    val image5 =  Image(512 , 512, image1.pixels ++ image2.pixels ++ image3.pixels ++ image4.pixels)
    val imageFile1 =  image5.output(new File("image1-1.png"))

    val image6 = image5.scaleTo(256, 256)
    val imageFile2 =  image6.output(new File("image2-2.png"))


    //println(imageFile.getAbsolutePath)
  }






}
