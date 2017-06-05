package timeusage

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfterAll, FunSuite}

@RunWith(classOf[JUnitRunner])
class TimeUsageSuite extends FunSuite with BeforeAndAfterAll {

    test("test start with") {
      val columnNames = List("t0101", "t0201", "t0102", "t0205", "t0301")
      val filtered = columnNames.filter(s => List("t01", "t02").exists(s.startsWith(_)))
      assert(filtered.size === 4)
    }

}
