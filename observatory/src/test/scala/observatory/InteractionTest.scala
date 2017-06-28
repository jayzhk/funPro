package observatory

import java.io.File

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
    val result = Interaction.tileLocation(2, 3, 3 )
    println(result)
  }

  test("generate tiles of 256 * 256") {
    val result = Extraction.locateTemperatures(2017, "/testStation.csv", "/2017.csv")
    val converted = Extraction.locationYearlyAverageRecords(result)

    val image = Interaction.tile(converted, points, 1, 0, 0)

    val imageFile =  image.output(new File("myimage.png"))
    //println(imageFile.getAbsolutePath)
  }

}
