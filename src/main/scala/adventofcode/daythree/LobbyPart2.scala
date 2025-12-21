package adventofcode.daythree

import adventofcode.daythree.input.BatteryBankParser
import adventofcode.daythree.model.{BatteryBank, Joltage}

import scala.util.{Failure, Success}

object LobbyPart2 extends App {

  private val maybeBatteryBanks = BatteryBankParser.readFile()

  maybeBatteryBanks match {
    case Failure(exception) => println(s"Error reading battery banks file: ${exception.getMessage}")
    case Success(batteryBanks) =>
      val sumOfJoltages: Joltage = batteryBanks.foldLeft(Joltage(0)) {
        case (sum: Joltage, batteryBank: BatteryBank) =>
          sum + batteryBank.largestJoltageOfTwelve()
      }

      println(s"Sum of highest joltages: ${sumOfJoltages.value}")
  }
}
