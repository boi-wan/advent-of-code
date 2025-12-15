package adventofcode.daythree.model

case class Battery(digit: Int) extends AnyVal with Ordered[Battery] {

  override def compare(that: Battery): Int = this.digit.compare(that.digit)
}
