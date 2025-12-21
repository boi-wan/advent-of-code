package adventofcode.daythree.model

import scala.collection.mutable

case class BatteryBank(batteries: Array[Battery]) extends AnyVal {
  def largestJoltage(): Joltage = {

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

    Joltage(s"${left._1.digit}${right._1.digit}".toInt)
  }

  def largestJoltageOfTwelve(): Joltage = {

    val targetJoltageSize: Int = 12

    def remainingBatteries(currentIndex: Int): Int = batteries.length - 1 - currentIndex

    def buildJoltageString(currentStack: mutable.Stack[Battery]): String = {
      currentStack.reverseIterator.map(_.digit).mkString
    }

    val joltage: mutable.Stack[Battery] = batteries.indices.foldLeft(new mutable.Stack[Battery]) {
      case (joltageStack, idx) =>
        val currentBattery = batteries(idx)

        while (joltageStack.nonEmpty && currentBattery > joltageStack.top && (remainingBatteries(idx) + joltageStack.size) >= targetJoltageSize) {
          joltageStack.pop()
        }

        if (joltageStack.size < targetJoltageSize) joltageStack.push(currentBattery)

        joltageStack
    }

    Joltage(buildJoltageString(joltage).toLong)
  }
}
