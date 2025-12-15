package adventofcode.daythree.model

case class BatteryBank(batteries: Array[Battery]) extends AnyVal {
  def largestJoltage(): Int = {

    def findHighest(range: Range): (Battery, Int) = {
      range.foldLeft(Battery(0), 0) {
        case ((highestBattery, highestIdx), index) =>
          if (batteries(index) > highestBattery) {
            (batteries(index), index)
          } else (highestBattery, highestIdx)
      }
    }

    val left: (Battery, Int) = findHighest(0 until (batteries.length - 1))

    val right: (Battery, Int) = findHighest((left._2 + 1) until batteries.length)

    s"${left._1.digit}${right._1.digit}".toInt
  }
}
