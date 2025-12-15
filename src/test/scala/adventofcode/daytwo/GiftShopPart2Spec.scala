package adventofcode.daytwo

import adventofcode.daytwo.input.RangeParser
import adventofcode.daytwo.model.{Range, SillyIdFinder}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.{Success, Try}

class GiftShopPart2Spec extends AnyWordSpec with Matchers {

  private class Fixture {
    val expectedRanges: List[Range] = List(
      Range(11, 22),
      Range(95, 115),
      Range(998, 1012),
      Range(1188511880, 1188511890),
      Range(222220, 222224),
      Range(1698522, 1698528),
      Range(446443, 446449),
      Range(38593856, 38593862),
      Range(565653, 565659),
      Range(824824821, 824824827),
      Range(2121212118L, 2121212124L)
    )
  }

  "GiftShopPart2" when {
    "reading the input file" should {
      "successfully parse all ranges" in new Fixture {
        val maybeRanges: Try[List[List[Range]]] = RangeParser.readFile()

        maybeRanges should be a Symbol("success")
        maybeRanges.get should have size 1
        maybeRanges.get.head should have size 11
      }

      "parse the correct range values" in new Fixture {
        val maybeRanges: Try[List[List[Range]]] = RangeParser.readFile()

        maybeRanges match {
          case Success(ranges) =>
            val rangeList = ranges.head
            rangeList(0) should be(Range(11, 22))
            rangeList(1) should be(Range(95, 115))
            rangeList(2) should be(Range(998, 1012))
            rangeList(3) should be(Range(1188511880, 1188511890))
            rangeList(4) should be(Range(222220, 222224))
            rangeList(5) should be(Range(1698522, 1698528))
            rangeList(6) should be(Range(446443, 446449))
            rangeList(7) should be(Range(38593856, 38593862))
            rangeList(8) should be(Range(565653, 565659))
            rangeList(9) should be(Range(824824821, 824824827))
            rangeList(10) should be(Range(2121212118L, 2121212124L))
          case _ => fail("Failed to parse ranges")
        }
      }
    }

    "processing ranges" should {
      "find silly IDs in each range" in new Fixture {
        val maybeRanges: Try[List[List[Range]]] = RangeParser.readFile()

        maybeRanges match {
          case Success(ranges) =>
            val sillyIds = ranges.head.map { range =>
              SillyIdFinder.findSillyIdsPart2(range)
            }

            sillyIds should have size 11
            sillyIds(0) should be(Set(BigDecimal(11), BigDecimal(22)))
            sillyIds(1) should be(Set(BigDecimal(99), BigDecimal(111)))
            sillyIds(2) should be(Set(BigDecimal(999), BigDecimal(1010)))
            sillyIds(3) should be(Set(BigDecimal(1188511885)))
            sillyIds(4) should be(Set(BigDecimal(222222)))
            sillyIds(5) should be(Set.empty)
            sillyIds(6) should be(Set(BigDecimal(446446)))
            sillyIds(7) should be(Set(BigDecimal(38593859)))
            sillyIds(8) should be(Set(BigDecimal(565656)))
            sillyIds(9) should be(Set(BigDecimal(824824824)))
            sillyIds(10) should be(Set(BigDecimal(2121212121L)))
          case _ => fail("Failed to parse ranges")
        }
      }

      "calculate the correct sum of all silly IDs" in new Fixture {
        val maybeRanges: Try[List[List[Range]]] = RangeParser.readFile()

        maybeRanges match {
          case Success(ranges) =>
            val sumOfSillyIds: BigDecimal = ranges.head.foldLeft(BigDecimal.apply(0)) {
              case (sum: BigDecimal, range: Range) =>
                val sillyIds = SillyIdFinder.findSillyIdsPart2(range)
                sum + sillyIds.sum
            }

            // Sum: 11 + 22 + 99 + 111 + 999 + 1010 + 1188511885 + 222222 + 446446 + 38593859 + 565656 + 824824824 + 2121212121
            sumOfSillyIds should be(BigDecimal("4174379265"))
          case _ => fail("Failed to parse ranges")
        }
      }

      "process all ranges correctly from start to finish" in new Fixture {
        val maybeRanges: Try[List[List[Range]]] = RangeParser.readFile()

        maybeRanges match {
          case Success(ranges) =>
            val results = ranges.head.map { range =>
              val sillyIds = SillyIdFinder.findSillyIdsPart2(range)
              (range, sillyIds, sillyIds.sum)
            }

            results should have size 11
            results.map(_._3).sum should be(BigDecimal("4174379265"))
          case _ => fail("Failed to parse ranges")
        }
      }
    }

    "analyzing each range individually" should {
      "process range 11-22 correctly" in new Fixture {
        val maybeRanges: Try[List[List[Range]]] = RangeParser.readFile()

        maybeRanges match {
          case Success(ranges) =>
            val range = ranges.head(0)
            val sillyIds = SillyIdFinder.findSillyIdsPart2(range)

            range should be(Range(11, 22))
            sillyIds should be(Set(BigDecimal(11), BigDecimal(22)))
            sillyIds.sum should be(BigDecimal(33))
          case _ => fail("Failed to parse ranges")
        }
      }

      "process range 95-115 correctly" in new Fixture {
        val maybeRanges: Try[List[List[Range]]] = RangeParser.readFile()

        maybeRanges match {
          case Success(ranges) =>
            val range = ranges.head(1)
            val sillyIds = SillyIdFinder.findSillyIdsPart2(range)

            range should be(Range(95, 115))
            sillyIds should be(Set(BigDecimal(99), BigDecimal(111)))
            sillyIds.sum should be(BigDecimal(210))
          case _ => fail("Failed to parse ranges")
        }
      }

      "process range 998-1012 correctly" in new Fixture {
        val maybeRanges: Try[List[List[Range]]] = RangeParser.readFile()

        maybeRanges match {
          case Success(ranges) =>
            val range = ranges.head(2)
            val sillyIds = SillyIdFinder.findSillyIdsPart2(range)

            range should be(Range(998, 1012))
            sillyIds should be(Set(BigDecimal(999), BigDecimal(1010)))
            sillyIds.sum should be(BigDecimal(2009))
          case _ => fail("Failed to parse ranges")
        }
      }

      "process range 1698522-1698528 correctly (empty result)" in new Fixture {
        val maybeRanges: Try[List[List[Range]]] = RangeParser.readFile()

        maybeRanges match {
          case Success(ranges) =>
            val range = ranges.head(5)
            val sillyIds = SillyIdFinder.findSillyIdsPart2(range)

            range should be(Range(1698522, 1698528))
            sillyIds should be(Set.empty)
            sillyIds.sum should be(BigDecimal(0))
          case _ => fail("Failed to parse ranges")
        }
      }

      "process range 446443-446449 correctly" in new Fixture {
        val maybeRanges: Try[List[List[Range]]] = RangeParser.readFile()

        maybeRanges match {
          case Success(ranges) =>
            val range = ranges.head(6)
            val sillyIds = SillyIdFinder.findSillyIdsPart2(range)

            range should be(Range(446443, 446449))
            sillyIds should be(Set(BigDecimal(446446)))
            sillyIds.sum should be(BigDecimal(446446))
          case _ => fail("Failed to parse ranges")
        }
      }
    }

    "with edge cases" should {
      "handle empty range list" in {
        val emptyRanges: List[Range] = List.empty

        val sumOfSillyIds: BigDecimal = emptyRanges.foldLeft(BigDecimal.apply(0)) {
          case (sum: BigDecimal, range: Range) =>
            val sillyIds = SillyIdFinder.findSillyIdsPart2(range)
            sum + sillyIds.sum
        }

        sumOfSillyIds should be(BigDecimal(0))
      }

      "handle single range with silly IDs" in {
        val singleRange = List(Range(11, 22))

        val sumOfSillyIds: BigDecimal = singleRange.foldLeft(BigDecimal.apply(0)) {
          case (sum: BigDecimal, range: Range) =>
            val sillyIds = SillyIdFinder.findSillyIdsPart2(range)
            sum + sillyIds.sum
        }

        sumOfSillyIds should be(BigDecimal(33))
      }

      "handle single range with no silly IDs" in {
        val singleRange = List(Range(1698522, 1698528))

        val sumOfSillyIds: BigDecimal = singleRange.foldLeft(BigDecimal.apply(0)) {
          case (sum: BigDecimal, range: Range) =>
            val sillyIds = SillyIdFinder.findSillyIdsPart2(range)
            sum + sillyIds.sum
        }

        sumOfSillyIds should be(BigDecimal(0))
      }

      "handle range with multiple silly IDs" in {
        val singleRange = List(Range(998, 1012))

        val sumOfSillyIds: BigDecimal = singleRange.foldLeft(BigDecimal.apply(0)) {
          case (sum: BigDecimal, range: Range) =>
            val sillyIds = SillyIdFinder.findSillyIdsPart2(range)
            sum + sillyIds.sum
        }

        sumOfSillyIds should be(BigDecimal(2009))
      }
    }
  }
}

