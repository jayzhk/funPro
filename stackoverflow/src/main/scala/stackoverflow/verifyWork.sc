val v = List( List(1, 2), List(2, 3))
val b = v.flatten

b.foldLeft((0, 0))((a, b) => (a._1 + b, a._2 + b))
//b.red

val v2 = List((1, 2), (2, 3), (4, 9), (5, 8), (2, 6))
v2.size
v2.groupBy(k => k._1).maxBy(k=>k._2.size)._1
(v2.groupBy(_._1).maxBy(_._2.size)._2.size.toDouble) / v2.size.toDouble
v2.groupBy(_._1).maxBy(_._2.size)._2.size

val list = List(0,1, 2, 3 ,4 ,5 ,6 ,7, 8, 9)
list(list.size /2)
list(list.size /2 - 1)
if(list.size % 2 == 1 ) list(list.size / 2) else
  (list(list.size /2) +
  list(list.size/2 -1)) /2
6.0d / 8







