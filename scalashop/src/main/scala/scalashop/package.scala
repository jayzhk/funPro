

package object scalashop {

  /** The value of every pixel is represented as a 32 bit integer. */
  type RGBA = Int

  /** Returns the red component. */
  def red(c: RGBA): Int = (0xff000000 & c) >>> 24

  /** Returns the green component. */
  def green(c: RGBA): Int = (0x00ff0000 & c) >>> 16

  /** Returns the blue component. */
  def blue(c: RGBA): Int = (0x0000ff00 & c) >>> 8

  /** Returns the alpha component. */
  def alpha(c: RGBA): Int = (0x000000ff & c) >>> 0

  /** Used to create an RGBA value from separate components. */
  def rgba(r: Int, g: Int, b: Int, a: Int): RGBA = {
    (r << 24) | (g << 16) | (b << 8) | (a << 0)
  }

  /** Restricts the integer into the specified range. */
  def clamp(v: Int, min: Int, max: Int): Int = {
    if (v < min) min
    else if (v > max) max
    else v
  }

  /** Image is a two-dimensional matrix of pixel values. */
  class Img(val width: Int, val height: Int, private val data: Array[RGBA]) {
    def this(w: Int, h: Int) = this(w, h, new Array(w * h))
    def apply(x: Int, y: Int): RGBA = data(y * width + x)
    def update(x: Int, y: Int, c: RGBA): Unit = data(y * width + x) = c
  }

  /** Computes the blurred RGBA value of a single pixel of the input image. */
  def boxBlurKernel(src: Img, x: Int, y: Int, radius: Int): RGBA = {

//      if(radius <= 0) src.apply(x, y)
//      else {
//        var r, g, b, a = 0
//        var i = clamp(x - radius, 0, x - radius)
//        var j = clamp(y - radius, 0, y - radius)
//        val avg = (x + radius + 1 - i) * (y + radius + 1 - j)
//          while(i <= x + radius ){
//            while(j <= y + radius){
//              val c = src(i, j)
//              r = r + red(c)
//              g = g + green(c)
//              b = b + blue(c)
//              a = a + alpha(c)
//              j = j + 1
//            }
//            i = i + 1
//            j = clamp(y - radius, 0, y - radius)
//          }
//
//        rgba(r/avg, g/avg, b/avg, a/avg)
//      }

    if(radius <= 0) src(x, y)
    else {

      val acc = for {
        i <- clamp(x - radius, 0, x - radius) to clamp(x + radius, x, src.width  - 1)
        j <- clamp(y - radius, 0, y - radius) to clamp(y + radius, y, src.height - 1)
        c = src(i, j)
      } yield c

      val length = acc.length
      val r = acc.map(red).sum     / length
      val g = (acc.map(green).sum) / length
      val b = (acc.map(blue).sum ) / length
      val a = (acc.map(alpha).sum) / length

    //  println(s"r = ${r} g = ${g} b = ${b} a = ${a}")

     // val combined = acc.reduce((x, y) => rgba(red(x) + red(y), green(x) + green(y), blue(x) + blue(y), alpha(x) + alpha(y)))
     // rgba(red(combined) / length, green(combined) / length, blue(combined) / length, alpha(combined) / length)
      rgba(r,g,b,a)
    }
  }
}
