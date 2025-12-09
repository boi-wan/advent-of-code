package com.paoloboi.adventofcode
package dayone.model

object Dial {

  private val DefaultSize: Size = Size(100)

  def default(position: Position): Dial = {
    new Dial(DefaultSize, position)
  }
}

case class Dial(size: Size, position: Position) {
  def doTurn(turn: Turn): Dial = {
    turn match {
      case l: LeftTurn => left(l)
      case r: RightTurn => right(r)
    }
  }

  def isZero: Boolean = this.position.value == 0

  private def left(turn: LeftTurn): Dial = {
    val factor = delta(-turn.value)
    val nextPosition = factor + (if (factor < 0) this.size.value else 0)
    new Dial(this.size, Position(nextPosition))
  }

  private def right(turn: RightTurn): Dial = {
    val nextPosition = delta(turn.value)
    new Dial(this.size, Position(nextPosition))
  }

  private def delta(turnValue: Int): Int = {
    (this.position.value + turnValue) % this.size.value
  }
}

case class Size(value: Int)

case class Position(value: Int)
