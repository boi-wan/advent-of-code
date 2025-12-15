package adventofcode.daythree

import adventofcode.daythree.input.BatteryBankParser
import adventofcode.daythree.model.BatteryBank

import scala.util.{Failure, Success}

object LobbyPart1 extends App {
  
  private val maybeBatteryBanks = BatteryBankParser.readFile()

  maybeBatteryBanks match {
    case Failure(exception) => println(s"Error reading battery banks file: ${exception.getMessage}")
    case Success(batteryBanks) =>
      val sumOfJoltages: BigDecimal = batteryBanks.foldLeft(BigDecimal.apply(0)) {
        case (sum: BigDecimal, batteryBank: BatteryBank) =>
          sum + batteryBank.largestJoltage()
      }

      println(s"Sum of highest joltages: $sumOfJoltages")
  }
}
