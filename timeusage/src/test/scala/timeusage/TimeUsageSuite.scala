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
      (5, 15, 20), (8, 20, 30),
      (7, 20, 50), (6, 15, 18))
    ).toDF("col1", "col2", "col3")

    val col1 = when(df("col1") > 0, "positive").otherwise("negative").as("pos")
    println(col1)

    val colList = List(df("col1"), df("col2"), df("col3"))
    val column = colList.reduce(_ + _)./(60).as("POS")
    df.select(column).show()

  //df.groupBy(df("col1"), df("col2"), df("col3")).sum().as("sumup").show()

  }
}
