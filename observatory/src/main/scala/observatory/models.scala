package observatory

case class Location(lat: Double, lon: Double)

case class Color(red: Int, green: Int, blue: Int) {
  def  - (that: Color) : (Int, Int, Int)  = {
    ((this.red - that.red), (this.green - that.green), (this.blue - that.blue))
  }
}

case class Key(stnId: String, wbanId: String)

