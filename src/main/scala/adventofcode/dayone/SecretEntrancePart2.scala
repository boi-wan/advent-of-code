package adventofcode.dayone

import adventofcode.dayone.input.TurnParser
import adventofcode.dayone.model.{Dial, DialZeroCounter, Position, ZeroesCount}

import scala.util.{Failure, Success}

object SecretEntrancePart2 extends App {

  private val maybeTurns = TurnParser.readFile()

  maybeTurns match {
    case Failure(exception) => println(s"Error reading turns file: ${exception.getMessage}")
    case Success(turns) => {

      val initialDial = Dial.default(position = Position(50))

      maybeTurns.get.foldLeft((initialDial, ZeroesCount.zero)) { case ((dial, zeroes), turn) =>
        val nextDial = dial.doTurn(turn)
        val howManyZeroes = DialZeroCounter.countZeroes(turn)(dial)
        (nextDial, zeroes + howManyZeroes)
      } match {
        case (finalDial, totalZeroes) =>
          println(s"Final dial position: ${finalDial.position.value}")
          println(s"Number of times dial hit zero: ${totalZeroes.value}")
      }
    }
  }
}
