package com.paoloboi.adventofcode
package dayone.input

import dayone.model.{LeftTurn, RightTurn, Turn}
import util.{FileParser, RowParser}

import scala.util.Try

sealed class TurnRowParser extends RowParser[Turn] {
  override def parse(row: String): Turn = {
    if (row.startsWith("L")) {
      LeftTurn(row.substring(1).toInt)
    } else if (row.startsWith("R")) {
      RightTurn(row.substring(1).toInt)
    } else {
      throw new IllegalArgumentException(s"Invalid turn format: $row")
    }
  }
}

object TurnParser {
  def readFile(): Try[List[Turn]] =
    FileParser(new TurnRowParser()).readFile("dayone/input.txt")
}


