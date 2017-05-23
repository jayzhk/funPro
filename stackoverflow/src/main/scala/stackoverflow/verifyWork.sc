val v = List( List(1, 2), List(2, 3))
val b = v.flatten

b.foldLeft((0, 0))((a, b) => (a._1 + b, a._2 + b))
//b.red

val v2 = List((1, 2), (2, 3), (4, 9), (5, 8), (2, 6))
v2.size
v2.groupBy(k => k._1).maxBy(k=>k._2.size)._1
(v2.groupBy(_._1).maxBy(_._2.size)._2.size.toDouble) / v2.size.toDouble
v2.groupBy(_._1).maxBy(_._2.size)._2.size






