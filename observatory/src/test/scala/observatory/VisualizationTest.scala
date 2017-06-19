package observatory


import java.io.File

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers

@RunWith(classOf[JUnitRunner])
class VisualizationTest extends FunSuite with Checkers {

  test("great cycle distance") {
    val loc1 = Location(37.358,-78.438)
    val loc2 = Location(37.350,-78.433)
    val distance = Visualization.greatCycleDistance(loc1, loc2)
    println(distance)
  }

  test("test predict temperature") {

    val result = Extraction.locateTemperatures(2015, "/testStation.csv", "/testTemperature.csv")
    val converted = Extraction.locationYearlyAverageRecords(result)
    println(converted)

    val loc3 = Location(37.37,-78.44)
    //val loc2 = Location(37.350,-78.433)
    val temp = Visualization.predictTemperature(converted, loc3)
    println(temp)

  }

  test("test linear interpolation") {
    val result = Visualization.linearInterpolate((32.0, Color(255, 0, 0)), (60.0, Color(255, 255 ,255)), 46 )
    println(result)
  }

  test("test interpolate color") {
    val points = Iterable((60.0, Color(255, 255, 255)), (32.0, Color(255, 0, 0)), (12.0, Color(255, 255, 0)), (0.0, Color(0, 255, 255)))

    val result = Visualization.interpolateColor(points, 22)
    println(result)

  }

  test("test interpolate color 2") {
    val points = List((0.0,Color(255,0,0)), (1.0,Color(0,0,255)))

    val result = Visualization.interpolateColor(points, 0.25)
    println(result)
    val points2 =  List((0.0,Color(255,0,0)), (571231.0,Color(0,0,255)))
    val result2 = Visualization.interpolateColor(points2, 285615.5)
    println(s" the result 2 = $result2")

    val points3 = List((-5.3552292E7,Color(255,0,0)), (0.0,Color(0,0,255)))
    val result3 = Visualization.interpolateColor(points3, -2.6776146E7)
    println(s"result 3 = $result3")
  }

  test("Test index where method") {
    val points = List(1, 2, 3, 4, 5)
    val index = points.indexWhere(p => p >= 2)
    println(index)
  }

  test("visualize") {

    val result = Extraction.locateTemperatures(2015, "/stations.csv", "/1975.csv")
    val converted = Extraction.locationYearlyAverageRecords(result)
    //println(converted)
    val scales = List((60.0, Color(255, 255, 255)), (32.0, Color(255, 0, 0)), (12.0, Color(255, 255, 0)), (0.0, Color(0, 255, 255)))
    val image = Visualization.visualize(converted, scales)
    val imageFile =  image.output(new File("myimage.png"))
    println(imageFile.getAbsolutePath)
  }

  test("convert string to integer") {
    val format = "+00.000"
    //val value = Integer.parseInt(format)
    val value = java.lang.Double.parseDouble(format)
  }

}
