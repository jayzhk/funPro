package observatory


import java.io.File

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers

@RunWith(classOf[JUnitRunner])
class VisualizationTest extends FunSuite with Checkers {

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

  test("great cycle distance") {
    val loc1 = Location(0, 0)
    val loc2 = Location(0, 0.01)
    println(math.sin(math.toRadians(90)))
    val distance = Visualization.greatCycleDistance(loc1, loc2)
    println(distance)
  }

//  test("test predict temperature") {
//
//    val result = Extraction.locateTemperatures(1975, "/stations.csv", "/1975.csv")
//    val converted = Extraction.locationYearlyAverageRecords(result)
//  //  println(converted)
//
//    val loc3 = Location(90.0,-180.0)
//    //val loc2 = Location(37.350,-78.433)
//    val temp = Visualization.predictTemperature(converted, loc3)
//    println(temp)
//    val points = Iterable((60.0, Color(255, 255, 255)), (32.0, Color(255, 0, 0)), (12.0, Color(255, 255, 0)), (0.0, Color(0, 255, 255)))
//
//    val r = Visualization.interpolateColor(points, temp)
//    println(r)
//
//  }

  test("test predict temperature 2") {
    val temp = Visualization.predictTemperature(List((Location(45.0,-90.0),-100.0), (Location(-45.0,0.0),1.0)), Location(90.0,-180.0))
    println(temp)
  }

  test("test linear interpolation") {
    val result = Visualization.linearInterpolate( (60.0, Color(255, 255 ,255)), (32.0, Color(255, 0, 0)), 46 )
    println(result)
  }

  test("test predict temperature and color interpolate"){
    //val temperature = List((Location(0.0,0.0),10.0))
   val temperature = List((Location(45.0,-90.0),10.0), (Location(-45.0,0.0),20.0))
    val location = Location(90.0, -180.0)
    val predicted = Visualization.predictTemperature(temperature, location)
    val points = List((1.0,Color(0,0,255)), (45.500012281326605,Color(255,0,0)))
    val color = Visualization.interpolateColor(points, predicted)

    println(s" predicted temperature = $predicted , color = $color" )


  }

  test("test interpolate color") {


    val result = Visualization.interpolateColor(points, -60)
    println(result)

  }

  test("test interpolate color 2") {
//    val points = List((0.0,Color(255,0,0)), (1.0,Color(0,0,255)))

    val result = Visualization.interpolateColor(points, 0.25)
    println(s"the result 1 = $result")
//    val points2 =  List((0.0,Color(255,0,0)), (571231.0,Color(0,0,255)))
    val result2 = Visualization.interpolateColor(points, 285615.5)
    println(s"the result 2 = $result2")

   // val points3 = List((-5.3552292E7,Color(255,0,0)), (0.0,Color(0,0,255)))
    val result3 = Visualization.interpolateColor(points, -2.6776146E7)
    println(s"the result 3 = $result3")

    val result4 = Visualization.interpolateColor(points, 33)
    println(s"the result 4 = $result4")
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
    //lazy val scales = List((60.0, Color(255, 255, 255)), (32.0, Color(255, 0, 0)), (12.0, Color(255, 255, 0)), (0.0, Color(0, 255, 255)))
    val image = Visualization.visualize(converted, points)
    val imageFile =  image.output(new File("myimage.png"))
    println(imageFile.getAbsolutePath)
  }

  test("convert string to integer") {
    val format = "+00.000"
    //val value = Integer.parseInt(format)
    val value = java.lang.Double.parseDouble(format)
  }

  test("for expression ") {
    val values = for{

      y <- 90 until -90 by -1
      x <- -180 until 180
    } yield (y, x)
    println(s"value size = ${values.size}")
    println(values)
  }

}
