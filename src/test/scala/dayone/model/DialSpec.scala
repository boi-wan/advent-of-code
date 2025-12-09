package adventofcode
package dayone.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DialSpec extends AnyWordSpec with Matchers {

  private class Fixture {
    private val defaultSize: Size = Size(100)
    private val position0: Position = Position(0)
    val position50: Position = Position(50)
    private val position99: Position = Position(99)

    val dialAt0: Dial = Dial(defaultSize, position0)
    val dialAt50: Dial = Dial(defaultSize, position50)
    val dialAt99: Dial = Dial(defaultSize, position99)
  }

  "Dial.default" should {
    "create a dial with default size and given position" in new Fixture {
      val dial: Dial = Dial.default(position50)

      dial.size should be(Size(100))
      dial.position should be(position50)
    }
  }

  "Dial.isZero" should {
    "return true when position is 0" in new Fixture {
      dialAt0.isZero should be(true)
    }

    "return false when position is not 0" in new Fixture {
      dialAt50.isZero should be(false)
      dialAt99.isZero should be(false)
    }
  }

  "Dial.doTurn" when {
    "turning right" should {
      "move position forward by the turn value" in new Fixture {
        val turn = RightTurn(10)
        val result: Dial = dialAt0.doTurn(turn)

        result.position should be(Position(10))
      }

      "wrap around when exceeding size" in new Fixture {
        val turn = RightTurn(10)
        val result: Dial = dialAt99.doTurn(turn)

        result.position should be(Position(9))
      }

      "stay at same position when turn value is 0" in new Fixture {
        val turn = RightTurn(0)
        val result: Dial = dialAt50.doTurn(turn)

        result.position should be(position50)
      }

      "wrap around exactly once" in new Fixture {
        val turn = RightTurn(100)
        val result: Dial = dialAt0.doTurn(turn)

        result.position should be(Position(0))
      }

      "handle large turn values with multiple wraps" in new Fixture {
        val turn = RightTurn(250)
        val result: Dial = dialAt0.doTurn(turn)

        result.position should be(Position(50))
      }
    }

    "turning left" should {
      "move position backward by the turn value" in new Fixture {
        val turn = LeftTurn(10)
        val result: Dial = dialAt50.doTurn(turn)

        result.position should be(Position(40))
      }

      "wrap around when going below 0" in new Fixture {
        val turn = LeftTurn(10)
        val result: Dial = dialAt0.doTurn(turn)

        result.position should be(Position(90))
      }

      "stay at same position when turn value is 0" in new Fixture {
        val turn = LeftTurn(0)
        val result: Dial = dialAt50.doTurn(turn)

        result.position should be(position50)
      }

      "wrap around exactly once" in new Fixture {
        val turn = LeftTurn(100)
        val result: Dial = dialAt0.doTurn(turn)

        result.position should be(Position(0))
      }

      "handle large turn values with multiple wraps" in new Fixture {
        val turn = LeftTurn(150)
        val result: Dial = dialAt0.doTurn(turn)

        result.position should be(Position(50))
      }

      "move from middle position to near end" in new Fixture {
        val turn = LeftTurn(60)
        val result: Dial = dialAt50.doTurn(turn)

        result.position should be(Position(90))
      }
    }

    "performing multiple turns" should {
      "combine right turns correctly" in new Fixture {
        val result: Dial = dialAt0
          .doTurn(RightTurn(30))
          .doTurn(RightTurn(25))

        result.position should be(Position(55))
      }

      "combine left turns correctly" in new Fixture {
        val result: Dial = dialAt99
          .doTurn(LeftTurn(30))
          .doTurn(LeftTurn(25))

        result.position should be(Position(44))
      }

      "combine right and left turns" in new Fixture {
        val result: Dial = dialAt50
          .doTurn(RightTurn(30))
          .doTurn(LeftTurn(20))

        result.position should be(Position(60))
      }

      "return to original position after complementary turns" in new Fixture {
        val result: Dial = dialAt50
          .doTurn(RightTurn(40))
          .doTurn(LeftTurn(40))

        result.position should be(position50)
      }
    }
  }

  "Dial with custom size" should {
    "work correctly with smaller size" in new Fixture {
      val smallDial = Dial(Size(10), Position(5))

      val result: Dial = smallDial.doTurn(RightTurn(8))
      result.position should be(Position(3))
    }

    "work correctly with right turn wrapping on custom size" in new Fixture {
      val customDial = Dial(Size(50), Position(45))

      val result: Dial = customDial.doTurn(RightTurn(10))
      result.position should be(Position(5))
    }

    "work correctly with left turn wrapping on custom size" in new Fixture {
      val customDial = Dial(Size(50), Position(5))

      val result: Dial = customDial.doTurn(LeftTurn(10))
      result.position should be(Position(45))
    }
  }
}

