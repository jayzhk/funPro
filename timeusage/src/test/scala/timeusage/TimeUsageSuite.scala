package timeusage

import org.apache.spark.sql.expressions.scalalang.typed
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfterAll, FunSuite}
import timeusage.TimeUsage.spark
import org.apache.spark.sql.functions._

@RunWith(classOf[JUnitRunner])
class TimeUsageSuite extends FunSuite with BeforeAndAfterAll {

  val columnNames = List("t0101", "t0201", "t0102", "t0205", "t0301")

   //lazy val conf: SparkConf = new SparkConf().setMaster("local").setAppName("timeTest")
   //lazy val sc: SparkContext = new SparkContext(conf)




    test("test start with") {
      val filtered = columnNames.filter(s => List("t01", "t02").exists(s.startsWith(_)))
      assert(filtered.size === 4)
    }

//  test("dfschema should correctly convert to struck type") {
//    val types = TimeUsage.dfSchema(columnNames)
//    types.printTreeString()
//  }

  test("test group and value converting") {

    val df = spark.createDataFrame(Seq(
      (5, 15, 20.72), (5, 15, 30.26),
      (7, 20, 50.68), (7, 20, 18.12))
    ).toDF("col1", "col2", "col3")

//    val col1 = when(df("col1") > 0, "positive").otherwise("negative").as("pos")
//    println(col1)
//
//    val colList = List(df("col1"), df("col2"), df("col3"))
//    val column = colList.reduce(_ + _)./(60).as("POS")
//    df.select(column).show()

  //df.groupBy(df("col1"), df("col2"), df("col3")).sum().as("sumup").show()
    import spark.implicits._

    val ds = df.as[Cube]
   val dsa =  ds.groupByKey(k => (k.col1, k.col2)).agg(round(typed.avg[Cube](k => k.col3), 1).as[Double])
    dsa.map(k => Cube(k._1._1, k._1._2, k._2)).as[Cube].show()

  }
}
case class Cube( col1: Int, col2: Int, col3: Double)