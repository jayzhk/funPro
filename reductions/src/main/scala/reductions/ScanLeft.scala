package reductions

/**
  * Created by AXIGI on 4/25/2017.
  */
object ScanLeft {

  def reduceSeg1[A](inp: Array[A], left: Int, right: Int, a0: A, f:(A,A) => A ): A = ???

  def mapSeg[A, B] (inp: Array[A], left: Int, right: Int, fi: (Int, A) => B, out: Array[B]): Unit = ???

  def scanLeft[A](inp: Array[A], a0: A, f: (A, A) => A, out: Array[A]) = {

    val fi = { (i: Int, v:A) => reduceSeg1(inp, 0, i, a0, f) }
    mapSeg(inp, 0, inp.length, fi, out)
    val last = inp.length -1
    out(last + 1) = f(out(last), inp(last))
  }

}
