package adventofcode.dayone

import adventofcode.dayone.input.TurnParser
import adventofcode.dayone.model.*
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.{Success, Try}

class SecretEntrancePart2Spec extends AnyWordSpec with Matchers {

  private class Fixture {
    val initialPosition: Position = Position(50)
    val initialDial: Dial = Dial.default(position = initialPosition)
  }

  "SecretEntrancePart2" when {
    "reading the input file" should {
      "successfully parse all turns" in new Fixture {
        val maybeTurns: Try[List[Turn]] = TurnParser.readFile()

        maybeTurns should be a Symbol("success")
        maybeTurns.get should have size 10
      }

      "parse the correct turn types and values" in new Fixture {
        val maybeTurns: Try[List[Turn]] = TurnParser.readFile()

        maybeTurns match {
          case Success(turns) =>
            turns(0) should be(LeftTurn(68))
            turns(1) should be(LeftTurn(30))
            turns(2) should be(RightTurn(48))
            turns(3) should be(LeftTurn(5))
            turns(4) should be(RightTurn(60))
            turns(5) should be(LeftTurn(55))
            turns(6) should be(LeftTurn(1))
            turns(7) should be(LeftTurn(99))
            turns(8) should be(RightTurn(14))
            turns(9) should be(LeftTurn(82))
          case _ => fail("Failed to parse turns")
        }
      }
    }

    "processing turns with zero crossing count" should {
      "calculate the correct final dial position" in new Fixture {
        val maybeTurns: Try[List[Turn]] = TurnParser.readFile()

        maybeTurns match {
          case Success(turns) =>
            val (finalDial, _) = turns.foldLeft((initialDial, ZeroesCount.zero)) { case ((dial, zeroes), turn) =>
              val nextDial = dial.doTurn(turn)
              val howManyZeroes = DialZeroCounter.countZeroes(turn)(dial)
              (nextDial, zeroes + howManyZeroes)
            }

            finalDial.position.value should be(32)
          case _ => fail("Failed to parse turns")
        }
      }

      "count the correct number of times the dial passes through zero" in new Fixture {
        val maybeTurns: Try[List[Turn]] = TurnParser.readFile()

        maybeTurns match {
          case Success(turns) =>
            val (_, totalZeroes) = turns.foldLeft((initialDial, ZeroesCount.zero)) { case ((dial, zeroes), turn) =>
              val nextDial = dial.doTurn(turn)
              val howManyZeroes = DialZeroCounter.countZeroes(turn)(dial)
              (nextDial, zeroes + howManyZeroes)
            }

            totalZeroes.value should be(6)
          case _ => fail("Failed to parse turns")
        }
      }

      "process all turns correctly from start to finish" in new Fixture {
        val maybeTurns: Try[List[Turn]] = TurnParser.readFile()

        maybeTurns match {
          case Success(turns) =>
            val (finalDial, totalZeroes) = turns.foldLeft((initialDial, ZeroesCount.zero)) { case ((dial, zeroes), turn) =>
              val nextDial = dial.doTurn(turn)
              val howManyZeroes = DialZeroCounter.countZeroes(turn)(dial)
              (nextDial, zeroes + howManyZeroes)
            }

            finalDial.position.value should be(32)
            totalZeroes.value should be(6)
          case _ => fail("Failed to parse turns")
        }
      }
    }

    "simulating the dial sequence step by step" should {
      "track zero crossings for each turn" in new Fixture {
        val maybeTurns: Try[List[Turn]] = TurnParser.readFile()

        maybeTurns match {
          case Success(turns) =>
            val results = turns.scanLeft((initialDial, ZeroesCount.zero)) { case ((dial, zeroes), turn) =>
              val nextDial = dial.doTurn(turn)
              val howManyZeroes = DialZeroCounter.countZeroes(turn)(dial)
              (nextDial, zeroes + howManyZeroes)
            }

            val positions = results.map(_._1.position.value)
            val zeroeCounts = results.map(_._2.value)

            positions(0) should be(50) // Initial position
            positions(1) should be(82) // After L68
            positions(2) should be(52) // After L30
            positions(3) should be(0) // After R48
            positions(4) should be(95) // After L5
            positions(5) should be(55) // After R60
            positions(6) should be(0) // After L55
            positions(7) should be(99) // After L1
            positions(8) should be(0) // After L99
            positions(9) should be(14) // After R14
            positions(10) should be(32) // After L82

            zeroeCounts(0) should be(0) // Initial
            zeroeCounts(1) should be(1) // After L68: crosses zero once
            zeroeCounts(2) should be(1) // After L30: no crossing
            zeroeCounts(3) should be(2) // After R48: crosses zero once (delta = 100)
            zeroeCounts(4) should be(2) // After L5: no crossing
            zeroeCounts(5) should be(3) // After R60: crosses zero once
            zeroeCounts(6) should be(4) // After L55: crosses zero once (delta = 100)
            zeroeCounts(7) should be(4) // After L1: no crossing
            zeroeCounts(8) should be(5) // After L99: crosses zero once (delta = 100)
            zeroeCounts(9) should be(5) // After R14: no crossing
            zeroeCounts(10) should be(6) // After L82: crosses zero once

          case _ => fail("Failed to parse turns")
        }
      }

      "correctly accumulate zero crossings" in new Fixture {
        val maybeTurns: Try[List[Turn]] = TurnParser.readFile()

        maybeTurns match {
          case Success(turns) =>
            val (dial1, zeroes1) = (initialDial, ZeroesCount.zero)
            val (dial2, zeroes2) = {
              val nextDial = dial1.doTurn(turns(0)) // L68 from 50
              val howManyZeroes = DialZeroCounter.countZeroes(turns(0))(dial1)
              (nextDial, zeroes1 + howManyZeroes)
            }

            dial2.position.value should be(82)
            zeroes2.value should be(1) // L68 from 50 crosses zero once

            val (dial3, zeroes3) = {
              val nextDial = dial2.doTurn(turns(4)) // R60 from 82 after some other turns
              val howManyZeroes = DialZeroCounter.countZeroes(turns(4))(dial2)
              (nextDial, zeroes2 + howManyZeroes)
            }

            // R60 from 82 would go to (82 + 60) % 100 = 42
            dial3.position.value should be(42)

          case _ => fail("Failed to parse turns")
        }
      }
    }

    "with edge cases" should {
      "handle empty turn list" in new Fixture {
        val emptyTurns: List[Nothing] = List.empty

        val (finalDial, totalZeroes) = emptyTurns.foldLeft((initialDial, ZeroesCount.zero)) { case ((dial, zeroes), turn) =>
          val nextDial = dial.doTurn(turn)
          val howManyZeroes = DialZeroCounter.countZeroes(turn)(dial)
          (nextDial, zeroes + howManyZeroes)
        }

        finalDial.position.value should be(50)
        totalZeroes.value should be(0)
      }

      "handle single turn without crossing zero" in new Fixture {
        val singleTurn = List(RightTurn(10))

        val (finalDial, totalZeroes) = singleTurn.foldLeft((initialDial, ZeroesCount.zero)) { case ((dial, zeroes), turn) =>
          val nextDial = dial.doTurn(turn)
          val howManyZeroes = DialZeroCounter.countZeroes(turn)(dial)
          (nextDial, zeroes + howManyZeroes)
        }

        finalDial.position.value should be(60)
        totalZeroes.value should be(0)
      }

      "handle single turn that crosses zero" in new Fixture {
        val singleTurn = List(RightTurn(60))

        val (finalDial, totalZeroes) = singleTurn.foldLeft((initialDial, ZeroesCount.zero)) { case ((dial, zeroes), turn) =>
          val nextDial = dial.doTurn(turn)
          val howManyZeroes = DialZeroCounter.countZeroes(turn)(dial)
          (nextDial, zeroes + howManyZeroes)
        }

        finalDial.position.value should be(10)
        totalZeroes.value should be(1)
      }

      "handle turn that crosses zero multiple times" in new Fixture {
        val multiCrossTurn = List(RightTurn(250))

        val (finalDial, totalZeroes) = multiCrossTurn.foldLeft((initialDial, ZeroesCount.zero)) { case ((dial, zeroes), turn) =>
          val nextDial = dial.doTurn(turn)
          val howManyZeroes = DialZeroCounter.countZeroes(turn)(dial)
          (nextDial, zeroes + howManyZeroes)
        }

        finalDial.position.value should be(0)
        totalZeroes.value should be(3)
      }
    }
  }
}
