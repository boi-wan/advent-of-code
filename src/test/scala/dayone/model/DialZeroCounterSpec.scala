package com.paoloboi.adventofcode
package dayone.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DialZeroCounterSpec extends AnyWordSpec with Matchers {

  private class Fixture {
    val dialAt0: Dial = Dial.default(Position(0))
    val dialAt50: Dial = Dial.default(Position(50))
    val dialAt99: Dial = Dial.default(Position(99))
  }

  "DialZeroCounter.countZeroes" when {
    "turning right" should {
      "count 0 when not crossing zero" in new Fixture {
        val turn = RightTurn(10)
        val zeroCount: ZeroesCount = DialZeroCounter.countZeroes(turn)(dialAt0)

        zeroCount.value should be(0)
      }

      "count 1 when wrapping around once" in new Fixture {
        val turn = RightTurn(1)
        val zeroCount: ZeroesCount = DialZeroCounter.countZeroes(turn)(dialAt99)

        zeroCount.value should be(1)
      }

      "count 1 when completing exactly one full rotation" in new Fixture {
        val turn = RightTurn(100)
        val zeroCount: ZeroesCount = DialZeroCounter.countZeroes(turn)(dialAt0)

        zeroCount.value should be(1)
      }

      "count 1 when wrapping around from position 99" in new Fixture {
        val turn = RightTurn(10)
        val zeroCount: ZeroesCount = DialZeroCounter.countZeroes(turn)(dialAt99)

        zeroCount.value should be(1)
      }

      "count multiple passes through zero" in new Fixture {
        val turn = RightTurn(250)
        val zeroCount: ZeroesCount = DialZeroCounter.countZeroes(turn)(dialAt0)

        zeroCount.value should be(2)
      }

      "count 0 for small turn from middle position" in new Fixture {
        val turn = RightTurn(30)
        val zeroCount: ZeroesCount = DialZeroCounter.countZeroes(turn)(dialAt50)

        zeroCount.value should be(0)
      }

      "count 1 when wrapping from middle position" in new Fixture {
        val turn = RightTurn(60)
        val zeroCount: ZeroesCount = DialZeroCounter.countZeroes(turn)(dialAt50)

        zeroCount.value should be(1)
      }
    }

    "turning left" should {
      "count 0 when not crossing zero" in new Fixture {
        val turn = LeftTurn(10)
        val zeroCount: ZeroesCount = DialZeroCounter.countZeroes(turn)(dialAt50)

        zeroCount.value should be(0)
      }

      "count 1 when landing on zero" in new Fixture {
        val turn = LeftTurn(50)
        val zeroCount: ZeroesCount = DialZeroCounter.countZeroes(turn)(dialAt50)

        zeroCount.value should be(1)
      }

      "count 1 when completing exactly one full rotation" in new Fixture {
        val turn = LeftTurn(100)
        val zeroCount: ZeroesCount = DialZeroCounter.countZeroes(turn)(dialAt0)

        zeroCount.value should be(1)
      }

      "count 0 when wrapping but not passing zero" in new Fixture {
        val turn = LeftTurn(10)
        val zeroCount: ZeroesCount = DialZeroCounter.countZeroes(turn)(dialAt0)

        zeroCount.value should be(0)
      }

      "count multiple passes through zero" in new Fixture {
        val turn = LeftTurn(250)
        val zeroCount: ZeroesCount = DialZeroCounter.countZeroes(turn)(dialAt0)

        zeroCount.value should be(2)
      }

      "count 1 when wrapping from middle position" in new Fixture {
        val turn = LeftTurn(60)
        val zeroCount: ZeroesCount = DialZeroCounter.countZeroes(turn)(dialAt50)

        zeroCount.value should be(1)
      }
    }

    "with custom dial size" should {
      "work correctly with smaller dial" in new Fixture {
        val smallDial = Dial(Size(10), Position(5))
        val turn = RightTurn(5)
        val zeroCount: ZeroesCount = DialZeroCounter.countZeroes(turn)(smallDial)

        zeroCount.value should be(1)
      }

      "count zero correctly when wrapping on custom size" in new Fixture {
        val customDial = Dial(Size(50), Position(45))
        val turn = RightTurn(5)
        val zeroCount: ZeroesCount = DialZeroCounter.countZeroes(turn)(customDial)

        zeroCount.value should be(1)
      }

      "handle left turns on custom size" in new Fixture {
        val customDial = Dial(Size(50), Position(5))
        val turn = LeftTurn(10)
        val zeroCount: ZeroesCount = DialZeroCounter.countZeroes(turn)(customDial)

        zeroCount.value should be(1)
      }
    }
  }

  "ZeroesCount" should {
    "have a zero factory method" in {
      val zero = ZeroesCount.zero

      zero.value should be(0)
    }

    "support addition with + operator" in {
      val count1 = ZeroesCount(5)
      val count2 = ZeroesCount(3)

      val result = count1 + count2

      result.value should be(8)
    }

    "support chained addition" in {
      val count1 = ZeroesCount(2)
      val count2 = ZeroesCount(3)
      val count3 = ZeroesCount(5)

      val result = count1 + count2 + count3

      result.value should be(10)
    }

    "add with zero correctly" in {
      val count = ZeroesCount(7)
      val zero = ZeroesCount.zero

      val result = count + zero

      result.value should be(7)
    }
  }
}
