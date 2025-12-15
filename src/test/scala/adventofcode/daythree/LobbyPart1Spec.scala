package adventofcode.daythree

import adventofcode.daythree.input.BatteryBankParser
import adventofcode.daythree.model.{Battery, BatteryBank}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.{Success, Try}

class LobbyPart1Spec extends AnyWordSpec with Matchers {

  private class Fixture {
    val sampleBatteryBank1: BatteryBank = BatteryBank(Array(9, 8, 7, 6, 5, 4, 3, 2, 1, 1, 1, 1, 1, 1, 1).map(Battery.apply))
    val sampleBatteryBank2: BatteryBank = BatteryBank(Array(8, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9).map(Battery.apply))
    val sampleBatteryBank3: BatteryBank = BatteryBank(Array(2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 7, 8).map(Battery.apply))
    val sampleBatteryBank4: BatteryBank = BatteryBank(Array(8, 1, 8, 1, 8, 1, 9, 1, 1, 1, 1, 2, 1, 1, 1).map(Battery.apply))
  }

  "LobbyPart1" when {
    "reading the input file" should {
      "successfully parse all battery banks" in new Fixture {
        val maybeBatteryBanks: Try[List[BatteryBank]] = BatteryBankParser.readFile()

        maybeBatteryBanks should be a Symbol("success")
        maybeBatteryBanks.get should have size 4
      }

      "parse battery banks with correct battery values" in new Fixture {
        val maybeBatteryBanks: Try[List[BatteryBank]] = BatteryBankParser.readFile()

        maybeBatteryBanks match {
          case Success(batteryBanks) =>
            batteryBanks.head.batteries should have length 15
            batteryBanks.head.batteries(0).digit should be(9)
            batteryBanks.head.batteries(1).digit should be(8)
            batteryBanks.head.batteries(14).digit should be(1)

            batteryBanks(1).batteries should have length 15
            batteryBanks(1).batteries(0).digit should be(8)
            batteryBanks(1).batteries(14).digit should be(9)

            batteryBanks(2).batteries should have length 15
            batteryBanks(2).batteries(13).digit should be(7)
            batteryBanks(2).batteries(14).digit should be(8)

            batteryBanks(3).batteries should have length 15
            batteryBanks(3).batteries(6).digit should be(9)
          case _ => fail("Failed to parse battery banks")
        }
      }
    }

    "calculating largest joltage" should {
      "find the correct joltage for first battery bank" in new Fixture {
        val joltage: Int = sampleBatteryBank1.largestJoltage()
        joltage should be(98)
      }

      "find the correct joltage for second battery bank" in new Fixture {
        val joltage: Int = sampleBatteryBank2.largestJoltage()
        joltage should be(89)
      }

      "find the correct joltage for third battery bank" in new Fixture {
        val joltage: Int = sampleBatteryBank3.largestJoltage()
        joltage should be(78)
      }

      "find the correct joltage for fourth battery bank" in new Fixture {
        val joltage: Int = sampleBatteryBank4.largestJoltage()
        joltage should be(92)
      }

      "calculate correct joltages from the actual input file" in new Fixture {
        val maybeBatteryBanks: Try[List[BatteryBank]] = BatteryBankParser.readFile()

        maybeBatteryBanks match {
          case Success(batteryBanks) =>
            batteryBanks.head.largestJoltage() should be(98)
            batteryBanks(1).largestJoltage() should be(89)
            batteryBanks(2).largestJoltage() should be(78)
            batteryBanks(3).largestJoltage() should be(92)
          case _ => fail("Failed to parse battery banks")
        }
      }
    }

    "calculating sum of joltages" should {
      "calculate the correct sum for all battery banks" in new Fixture {
        val maybeBatteryBanks: Try[List[BatteryBank]] = BatteryBankParser.readFile()

        maybeBatteryBanks match {
          case Success(batteryBanks) =>
            val sumOfJoltages: BigDecimal = batteryBanks.foldLeft(BigDecimal.apply(0)) {
              case (sum: BigDecimal, batteryBank: BatteryBank) =>
                sum + batteryBank.largestJoltage()
            }

            sumOfJoltages should be(BigDecimal(357))
          case _ => fail("Failed to parse battery banks")
        }
      }

      "handle empty list of battery banks" in new Fixture {
        val emptyBatteryBanks: List[BatteryBank] = List.empty

        val sumOfJoltages: BigDecimal = emptyBatteryBanks.foldLeft(BigDecimal.apply(0)) {
          case (sum: BigDecimal, batteryBank: BatteryBank) =>
            sum + batteryBank.largestJoltage()
        }

        sumOfJoltages should be(BigDecimal(0))
      }

      "handle single battery bank" in new Fixture {
        val singleBatteryBank = List(sampleBatteryBank1)

        val sumOfJoltages: BigDecimal = singleBatteryBank.foldLeft(BigDecimal.apply(0)) {
          case (sum: BigDecimal, batteryBank: BatteryBank) =>
            sum + batteryBank.largestJoltage()
        }

        sumOfJoltages should be(BigDecimal(98))
      }
    }

    "with edge cases" should {
      "handle battery bank with all same values" in new Fixture {
        val uniformBatteryBank = BatteryBank(Array.fill(10)(Battery(5)))
        val joltage: Int = uniformBatteryBank.largestJoltage()
        joltage should be(55)
      }

      "handle battery bank with two batteries" in new Fixture {
        val twoElementBank = BatteryBank(Array(Battery(7), Battery(3)))
        val joltage: Int = twoElementBank.largestJoltage()
        joltage should be(73)
      }

      "handle battery bank with highest at the end" in new Fixture {
        val batteryBank = BatteryBank(Array(1, 2, 3, 4, 5, 9).map(Battery.apply))
        val joltage: Int = batteryBank.largestJoltage()
        joltage should be(59)
      }

      "handle battery bank with highest at the beginning" in new Fixture {
        val batteryBank = BatteryBank(Array(9, 1, 2, 3, 4, 5).map(Battery.apply))
        val joltage: Int = batteryBank.largestJoltage()
        joltage should be(95)
      }
    }
  }
}
