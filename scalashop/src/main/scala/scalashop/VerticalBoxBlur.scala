package scalashop

import org.scalameter._


object VerticalBoxBlurRunner {

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 5,
    Key.exec.maxWarmupRuns -> 10,
    Key.exec.benchRuns -> 10,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def main(args: Array[String]): Unit = {
    val radius = 3
    val width = 1920
    val height = 1080
    val src = new Img(width, height)
    val dst = new Img(width, height)
    val seqtime = standardConfig measure {
      VerticalBoxBlur.blur(src, dst, 0, width, radius)
    }
    println(s"sequential blur time: $seqtime ms")

    val numTasks = 32
    val partime = standardConfig measure {
      VerticalBoxBlur.parBlur(src, dst, numTasks, radius)
    }
    println(s"fork/join blur time: $partime ms")
    println(s"speedup: ${seqtime / partime}")
  }

}

/** A simple, trivially parallelizable computation. */
object VerticalBoxBlur {

  /** Blurs the columns of the source image `src` into the destination image
   *  `dst`, starting with `from` and ending with `end` (non-inclusive).
   *
   *  Within each column, `blur` traverses the pixels by going from top to
   *  bottom.
   */
  def blur(src: Img, dst: Img, from: Int, end: Int, radius: Int): Unit = {
     for {
       x <- from until end
       y <- 0 until src.height
     } dst.update(x, y, boxBlurKernel(src, x, y, radius))

  }

  /** Blurs the columns of the source image in parallel using `numTasks` tasks.
   *
   *  Parallelization is done by stripping the source image `src` into
   *  `numTasks` separate strips, where each strip is composed of some number of
   *  columns.
   */
  def parBlur(src: Img, dst: Img, numTasks: Int, radius: Int): Unit = {

    println(s"V src height = ${src.height} and src width = ${src.width}  and numOfTask = ${numTasks}")


    if (numTasks == 1) blur(src, dst, 0, src.width, radius)
    else {
      var step = src.width / numTasks
      if (step <= 1) {
        step = 1
      }

      val batch = (0 to src.width).by(step)
      val combine = batch zip batch.tail

     println(s"V Identified ranges = ${combine}")

      val tasks = for {
        (start, end) <- combine
        work = common.task { blur(src, dst, start, end, radius)
        }
      } yield work

      tasks.foreach(_.join)

      if (src.width % step > 0) {
        val endTask = common.task(blur(src, dst, (src.width - src.width % step), src.width, radius))
        println(s"V Last Task = ${src.width - src.width % step}  ${src.width}")
        endTask.join
      }
    }
  }

}

