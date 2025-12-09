package com.paoloboi.adventofcode
package dayone.model

object DialZeroCounter {
  def countZeroes(turn: Turn)(dial: Dial): ZeroesCount = {
    turn match {
      case l: LeftTurn => zeroCount(l)(dial)
      case r: RightTurn => zeroCount(r)(dial)
    }
  }

  private def zeroCount(turn: RightTurn)(dial: Dial): ZeroesCount = {
    val delta = dial.position.value + turn.value
    ZeroesCount(delta / dial.size.value)
  }

  private def zeroCount(turn: LeftTurn)(dial: Dial): ZeroesCount = {
    val delta = (dial.size.value - 1) - ((dial.position.value + 99) % dial.size.value) + turn.value
    ZeroesCount(delta / dial.size.value)
  }
}

object ZeroesCount {
  def zero: ZeroesCount = ZeroesCount(0)
}

sealed case class ZeroesCount(value: Int) {
  def +(other: ZeroesCount): ZeroesCount = ZeroesCount(value + other.value)
}
