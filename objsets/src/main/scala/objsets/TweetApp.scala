package objsets

/**
  * Created by huajiezeng on 3/21/17.
  */
object TweetApp {

  def main(args: Array[String]): Unit = {
    val a = new Empty
    val b = new Empty
    val c = a
    println(a.toString + " " + a.hashCode())
    println(b.toString + " " + b.hashCode())
    println(a == c)
  }

}
