package adventofcode.dayone.model

sealed trait Turn:
  val value: Int

case class LeftTurn(value: Int) extends Turn

case class RightTurn(value: Int) extends Turn
