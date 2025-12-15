package adventofcode.daythree.input

import adventofcode.daythree.model.{Battery, BatteryBank}
import adventofcode.util.{FileParser, RowParser}

import scala.util.Try

private sealed class BatteryBankParser extends RowParser[BatteryBank] {
  override def parse(row: String): BatteryBank = {
    BatteryBank(row.map(c => Battery(c.asDigit)).toArray)
  }
}

object BatteryBankParser {

  def readFile(): Try[List[BatteryBank]] =
    FileParser(new BatteryBankParser()).readFile("daythree/input.txt")
}
