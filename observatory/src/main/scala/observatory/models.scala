package observatory

case class Location(lat: Double, lon: Double)

case class Color(red: Int, green: Int, blue: Int) {
  def  - (that: Color) : (Int, Int, Int)  = {
    (math.abs(this.red - that.red), math.abs(this.green - that.green), math.abs(this.blue - that.blue))
  }
}

case class Key(stnId: String, wbanId: String)

