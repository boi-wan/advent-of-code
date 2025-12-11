package adventofcode.daytwo

import adventofcode.daytwo.input.RangeParser
import adventofcode.daytwo.model.{Range, SillyIdFinder}

import scala.util.{Failure, Success}

object GiftShopPart1 extends App {

  private val maybeRanges = RangeParser.readFile()

  maybeRanges match {
    case Failure(exception) => println(s"Error reading ranges file: ${exception.getMessage}")
    case Success(ranges) =>
      val sumOfSillyIds: BigDecimal = ranges.head.foldLeft(BigDecimal.apply(0)) {
        case (sum: BigDecimal, range: Range) =>
          val sillyIds = SillyIdFinder.findSillyIds(range)
          sum + sillyIds.sum
      }

      println(s"Sum of silly product IDs found: $sumOfSillyIds")
  }
}
