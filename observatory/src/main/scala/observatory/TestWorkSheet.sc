val a = Array(1, 2, 3)
val b = Array(4, 5, 6)
a ++ b

val v = for {
  a <- 1 to 3
  b <- 10 to 30 by 10
  c <- 4 to 6
  d <- 4 to 6
} yield (a, b, c, d)

v.foreach(println)

