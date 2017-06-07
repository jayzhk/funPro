package timeusage

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
      (1.0, 0.3, 1.0), (1.0, 0.5, 0.0),
      (-1.0, 0.6, 0.5), (-1.0, 5.6, 0.2))
    ).toDF("col1", "col2", "col3")

    val col1 = when(df("col1") > 0, "positive").otherwise("negative").as("pos")
    println(col1)

    df.groupBy(df("col1")).sum().show()

  }
}
