package adventofcode.daythree.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class BatteryBankSpec extends AnyWordSpec with Matchers {

  private class Fixture {
    val simpleTwoBatteries: BatteryBank = BatteryBank(Array(Battery(7), Battery(3)))
    val uniformBatteries: BatteryBank = BatteryBank(Array.fill(10)(Battery(5)))
    val ascendingBatteries: BatteryBank = BatteryBank(Array(1, 2, 3, 4, 5, 6, 7, 8, 9).map(Battery.apply))
    val descendingBatteries: BatteryBank = BatteryBank(Array(9, 8, 7, 6, 5, 4, 3, 2, 1).map(Battery.apply))
    val highestAtStart: BatteryBank = BatteryBank(Array(9, 1, 2, 3, 4, 5).map(Battery.apply))
    val highestAtEnd: BatteryBank = BatteryBank(Array(1, 2, 3, 4, 5, 9).map(Battery.apply))
    val highestInMiddle: BatteryBank = BatteryBank(Array(1, 2, 9, 4, 5, 6).map(Battery.apply))
    val twoHighestAdjacent: BatteryBank = BatteryBank(Array(1, 2, 8, 9, 4, 5).map(Battery.apply))
    val twoHighestSeparated: BatteryBank = BatteryBank(Array(9, 2, 3, 4, 5, 8).map(Battery.apply))

    // Real examples from the problem
    val example1: BatteryBank = BatteryBank(Array(9, 8, 7, 6, 5, 4, 3, 2, 1, 1, 1, 1, 1, 1, 1).map(Battery.apply))
    val example2: BatteryBank = BatteryBank(Array(8, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9).map(Battery.apply))
    val example3: BatteryBank = BatteryBank(Array(2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 7, 8).map(Battery.apply))
    val example4: BatteryBank = BatteryBank(Array(8, 1, 8, 1, 8, 1, 9, 1, 1, 1, 1, 2, 1, 1, 1).map(Battery.apply))
  }

  "BatteryBank" when {
    "created with valid batteries array" should {
      "store the batteries" in new Fixture {
        val batteries = Array(Battery(1), Battery(2), Battery(3))
        val bank = BatteryBank(batteries)

        bank.batteries should have length 3
        bank.batteries(0).digit should be(1)
        bank.batteries(1).digit should be(2)
        bank.batteries(2).digit should be(3)
      }
    }
  }

  "BatteryBank.largestJoltage" when {
    "working with simple cases" should {
      "calculate correct joltage for two batteries" in new Fixture {
        val joltage: Int = simpleTwoBatteries.largestJoltage()

        joltage should be(73)
      }

      "calculate correct joltage for uniform batteries" in new Fixture {
        val joltage: Int = uniformBatteries.largestJoltage()

        joltage should be(55)
      }

      "calculate correct joltage for ascending sequence" in new Fixture {
        val joltage: Int = ascendingBatteries.largestJoltage()

        joltage should be(89)
      }

      "calculate correct joltage for descending sequence" in new Fixture {
        val joltage: Int = descendingBatteries.largestJoltage()

        joltage should be(98)
      }
    }

    "working with different highest positions" should {
      "find highest at start and second highest later" in new Fixture {
        val joltage: Int = highestAtStart.largestJoltage()

        joltage should be(95)
      }

      "find highest not at start, then highest after that" in new Fixture {
        val joltage: Int = highestAtEnd.largestJoltage()

        joltage should be(59)
      }

      "find highest in middle of first partition" in new Fixture {
        val joltage: Int = highestInMiddle.largestJoltage()

        joltage should be(96)
      }
    }

    "working with multiple high values" should {
      "handle two highest values adjacent to each other" in new Fixture {
        val joltage: Int = twoHighestAdjacent.largestJoltage()

        joltage should be(95)
      }

      "handle two highest values separated" in new Fixture {
        val joltage: Int = twoHighestSeparated.largestJoltage()

        joltage should be(98)
      }
    }

    "working with real problem examples" should {
      "calculate correct joltage for example 1 (987654321111111)" in new Fixture {
        val joltage: Int = example1.largestJoltage()

        joltage should be(98)
      }

      "calculate correct joltage for example 2 (811111111111119)" in new Fixture {
        val joltage: Int = example2.largestJoltage()

        joltage should be(89)
      }

      "calculate correct joltage for example 3 (234234234234278)" in new Fixture {
        val joltage: Int = example3.largestJoltage()

        joltage should be(78)
      }

      "calculate correct joltage for example 4 (818181911112111)" in new Fixture {
        val joltage: Int = example4.largestJoltage()

        joltage should be(92)
      }
    }

    "working with edge cases" should {
      "handle battery bank with all zeros" in new Fixture {
        val allZeros = BatteryBank(Array.fill(5)(Battery(0)))
        val joltage: Int = allZeros.largestJoltage()

        joltage should be(0)
      }

      "handle battery bank with single high value at start" in new Fixture {
        val singleHigh = BatteryBank(Array(9, 0, 0, 0, 0).map(Battery.apply))
        val joltage: Int = singleHigh.largestJoltage()

        joltage should be(90)
      }

      "handle battery bank with single high value at end" in new Fixture {
        val singleHigh = BatteryBank(Array(0, 0, 0, 0, 9).map(Battery.apply))
        val joltage: Int = singleHigh.largestJoltage()

        joltage should be(9)
      }

      "handle battery bank with alternating high and low values" in new Fixture {
        val alternating = BatteryBank(Array(9, 1, 8, 1, 7, 1).map(Battery.apply))
        val joltage: Int = alternating.largestJoltage()

        joltage should be(98)
      }

      "handle battery bank with three batteries" in new Fixture {
        val threeBatteries = BatteryBank(Array(5, 9, 3).map(Battery.apply))
        val joltage: Int = threeBatteries.largestJoltage()

        joltage should be(93)
      }

      "handle battery bank where second partition is just one element" in new Fixture {
        val oneLast = BatteryBank(Array(1, 2, 3, 9).map(Battery.apply))
        val joltage: Int = oneLast.largestJoltage()

        joltage should be(39)
      }
    }

    "demonstrating the algorithm" should {
      "split array correctly - finds highest in [0, n-2] then [leftIdx+1, n-1]" in new Fixture {
        // Array: [5, 7, 3, 9, 2, 8]
        // First partition [0, 4]: highest is 9 at index 3
        // Second partition [4, 5]: highest is 8 at index 5
        // Result: 98
        val testBank = BatteryBank(Array(5, 7, 3, 9, 2, 8).map(Battery.apply))
        val joltage: Int = testBank.largestJoltage()

        joltage should be(98)
      }

      "ensure first partition excludes last element" in new Fixture {
        // Array: [1, 2, 3, 4, 9]
        // First partition [0, 3]: highest is 4 at index 3
        // Second partition [4, 4]: highest is 9 at index 4
        // Result: 49
        val testBank = BatteryBank(Array(1, 2, 3, 4, 9).map(Battery.apply))
        val joltage: Int = testBank.largestJoltage()

        joltage should be(49)
      }

      "ensure second partition starts after first highest" in new Fixture {
        // Array: [9, 2, 3, 4, 5]
        // First partition [0, 3]: highest is 9 at index 0
        // Second partition [1, 4]: highest is 5 at index 4
        // Result: 95
        val testBank = BatteryBank(Array(9, 2, 3, 4, 5).map(Battery.apply))
        val joltage: Int = testBank.largestJoltage()

        joltage should be(95)
      }
    }

    "working with numeric edge values" should {
      "handle maximum single digit values" in new Fixture {
        val maxDigits = BatteryBank(Array(9, 9).map(Battery.apply))
        val joltage: Int = maxDigits.largestJoltage()

        joltage should be(99)
      }

      "produce correct two-digit numbers" in new Fixture {
        val bank1 = BatteryBank(Array(1, 0, 0, 0, 2).map(Battery.apply))
        bank1.largestJoltage() should be(12)

        val bank2 = BatteryBank(Array(3, 0, 0, 0, 7).map(Battery.apply))
        bank2.largestJoltage() should be(37)

        val bank3 = BatteryBank(Array(6, 0, 0, 0, 4).map(Battery.apply))
        bank3.largestJoltage() should be(64)
      }
    }
  }
}
