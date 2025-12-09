package com.paoloboi.adventofcode
package dayone

import dayone.input.TurnParser
import dayone.model.{Dial, Position}

import scala.util.{Failure, Success}

object SecretEntrancePart1 extends App {

  private val maybeTurns = TurnParser.readFile()

  maybeTurns match {
    case Failure(exception) => println(s"Error reading turns file: ${exception.getMessage}")
    case Success(turns) => {

      val initialDial = Dial.default(position = Position(50))

      val (finalDial, zeroes) = maybeTurns.get.foldLeft((initialDial, 0)) { case ((dial, zeroes), turn) =>
        val nextDial = dial.doTurn(turn)
        (nextDial, zeroes + (if (nextDial.isZero) 1 else 0))
      }

      println(s"Final dial position: ${finalDial.position.value}")
      println(s"Number of times dial hit zero: $zeroes")
    }
  }
}
