package adventofcode.daytwo.input

import adventofcode.daytwo.model.Range
import adventofcode.util.{FileParser, RowParser}

import scala.util.Try

private sealed class RangeParser extends RowParser[List[Range]] {
  override def parse(row: String): List[Range] = {

    val rawRanges = row.split(',')
    rawRanges.toList.map {
      rawRange =>
        val rawLimits = rawRange.split('-')
        if (rawLimits.length != 2) {
          throw new IllegalArgumentException(s"Invalid range format: $rawRange")
        }
        else {
          Range(BigDecimal.apply(rawLimits.head), BigDecimal.apply(rawLimits.last))
        }
    }
  }
}

object RangeParser {

  def readFile(): Try[List[List[Range]]] =
    FileParser(new RangeParser()).readFile("daytwo/input.txt")
}
