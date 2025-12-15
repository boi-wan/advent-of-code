package adventofcode.dayone

import adventofcode.dayone.input.TurnParser
import adventofcode.dayone.model.*
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.{Success, Try}

class SecretEntrancePart1Spec extends AnyWordSpec with Matchers {

  private class Fixture {
    val initialPosition: Position = Position(50)
    val initialDial: Dial = Dial.default(position = initialPosition)
  }

  "SecretEntrancePart1" when {
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

    "processing turns" should {
      "calculate the correct final dial position" in new Fixture {
        val maybeTurns: Try[List[Turn]] = TurnParser.readFile()

        maybeTurns match {
          case Success(turns) =>
            val (finalDial, _) = turns.foldLeft((initialDial, 0)) { case ((dial, zeroes), turn) =>
              val nextDial = dial.doTurn(turn)
              (nextDial, zeroes + (if (nextDial.isZero) 1 else 0))
            }

            finalDial.position.value should be(32)
          case _ => fail("Failed to parse turns")
        }
      }

      "count the correct number of times the dial hits zero" in new Fixture {
        val maybeTurns: Try[List[Turn]] = TurnParser.readFile()

        maybeTurns match {
          case Success(turns) =>
            val (_, zeroes) = turns.foldLeft((initialDial, 0)) { case ((dial, zeroes), turn) =>
              val nextDial = dial.doTurn(turn)
              (nextDial, zeroes + (if (nextDial.isZero) 1 else 0))
            }

            zeroes should be(3)
          case _ => fail("Failed to parse turns")
        }
      }

      "process all turns correctly from start to finish" in new Fixture {
        val maybeTurns: Try[List[Turn]] = TurnParser.readFile()

        maybeTurns match {
          case Success(turns) =>
            val (finalDial, zeroes) = turns.foldLeft((initialDial, 0)) { case ((dial, zeroes), turn) =>
              val nextDial = dial.doTurn(turn)
              (nextDial, zeroes + (if (nextDial.isZero) 1 else 0))
            }

            finalDial.position.value should be(32)
            zeroes should be(3)
          case _ => fail("Failed to parse turns")
        }
      }
    }

    "simulating the dial sequence step by step" should {
      "follow the expected position transitions" in new Fixture {
        val maybeTurns: Try[List[Turn]] = TurnParser.readFile()

        maybeTurns match {
          case Success(turns) =>
            val positions = turns.scanLeft(initialDial)((dial, turn) => dial.doTurn(turn))
              .map(_.position.value)

            positions(0) should be(50) // Initial position
            positions(1) should be(82) // After L68: (50 - 68 + 100) % 100 = 82
            positions(2) should be(52) // After L30: (82 - 30) % 100 = 52
            positions(3) should be(0) // After R48: (52 + 48) % 100 = 0 (ZERO!)
            positions(4) should be(95) // After L5: (0 - 5 + 100) % 100 = 95
            positions(5) should be(55) // After R60: (95 + 60) % 100 = 55
            positions(6) should be(0) // After L55: (55 - 55) % 100 = 0 (ZERO!)
            positions(7) should be(99) // After L1: (0 - 1 + 100) % 100 = 99
            positions(8) should be(0) // After L99: (99 - 99) % 100 = 0 (ZERO!)
            positions(9) should be(14) // After R14: (0 + 14) % 100 = 14
            positions(10) should be(32) // After L82: (14 - 82 + 100) % 100 = 32

          case _ => fail("Failed to parse turns")
        }
      }

      "correctly identify each time the dial passes through zero" in new Fixture {
        val maybeTurns: Try[List[Turn]] = TurnParser.readFile()

        maybeTurns match {
          case Success(turns) =>
            val zeroPositions = turns.scanLeft(initialDial)((dial, turn) => dial.doTurn(turn))
              .map(_.isZero)
              .zipWithIndex
              .filter(_._1)
              .map(_._2)

            zeroPositions should contain theSameElementsAs List(3, 6, 8)

          case _ => fail("Failed to parse turns")
        }
      }
    }

    "with edge cases" should {
      "handle empty turn list" in new Fixture {
        val emptyTurns: List[Nothing] = List.empty

        val (finalDial, zeroes) = emptyTurns.foldLeft((initialDial, 0)) { case ((dial, zeroes), turn) =>
          val nextDial = dial.doTurn(turn)
          (nextDial, zeroes + (if (nextDial.isZero) 1 else 0))
        }

        finalDial.position.value should be(50)
        zeroes should be(0)
      }

      "handle single turn" in new Fixture {
        val singleTurn = List(RightTurn(10))

        val (finalDial, zeroes) = singleTurn.foldLeft((initialDial, 0)) { case ((dial, zeroes), turn) =>
          val nextDial = dial.doTurn(turn)
          (nextDial, zeroes + (if (nextDial.isZero) 1 else 0))
        }

        finalDial.position.value should be(60)
        zeroes should be(0)
      }

      "handle turn that lands on zero" in new Fixture {
        val turnToZero = List(LeftTurn(50))

        val (finalDial, zeroes) = turnToZero.foldLeft((initialDial, 0)) { case ((dial, zeroes), turn) =>
          val nextDial = dial.doTurn(turn)
          (nextDial, zeroes + (if (nextDial.isZero) 1 else 0))
        }

        finalDial.position.value should be(0)
        zeroes should be(1)
      }
    }
  }
}

