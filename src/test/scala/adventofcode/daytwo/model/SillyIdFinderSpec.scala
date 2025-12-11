package adventofcode.daytwo.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class SillyIdFinderSpec extends AnyWordSpec with Matchers {

  private class Fixture {
    // SillyIdFinder is an object, not a class
  }

  "SillyIdFinder.findSillyIds" when {
    "given the 11-22 range" should {
      "return a non empty set" in new Fixture {
        val range = Range(11, 22)
        val result: Set[BigDecimal] = SillyIdFinder.findSillyIds(range)

        result should be(Set(BigDecimal(11), BigDecimal(22)))
      }
    }

    "given the 95-115 range" should {
      "return a non empty set" in new Fixture {
        val range = Range(95, 115)
        val result: Set[BigDecimal] = SillyIdFinder.findSillyIds(range)

        result should be(Set(BigDecimal(99)))
      }
    }

    "given the 998-1012 range" should {
      "return a non empty set" in new Fixture {
        val range = Range(998, 1012)
        val result: Set[BigDecimal] = SillyIdFinder.findSillyIds(range)

        result should be(Set(BigDecimal(1010)))
      }
    }

    "given the 1188511880-1188511890 range" should {
      "return a non empty set" in new Fixture {
        val range = Range(1188511880, 1188511890)
        val result: Set[BigDecimal] = SillyIdFinder.findSillyIds(range)

        result should be(Set(BigDecimal(1188511885)))
      }
    }

    "given the 222220-222224 range" should {
      "return a non empty set" in new Fixture {
        val range = Range(222220, 222224)
        val result: Set[BigDecimal] = SillyIdFinder.findSillyIds(range)

        result should be(Set(BigDecimal(222222)))
      }
    }

    "given the 1698522-1698528 range" should {
      "return a non empty set" in new Fixture {
        val range = Range(1698522, 1698528)
        val result: Set[BigDecimal] = SillyIdFinder.findSillyIds(range)

        result should be(Set.empty)
      }
    }

    "given the 446443-446449 range" should {
      "return a non empty set" in new Fixture {
        val range = Range(446443, 446449)
        val result: Set[BigDecimal] = SillyIdFinder.findSillyIds(range)

        result should be(Set(BigDecimal(446446)))
      }
    }

    "given the 38593856-38593862 range" should {
      "return a non empty set" in new Fixture {
        val range = Range(38593856, 38593862)
        val result: Set[BigDecimal] = SillyIdFinder.findSillyIds(range)

        result should be(Set(BigDecimal(38593859)))
      }
    }

    "given the 565653-565659 range" should {
      "return a non empty set" in new Fixture {
        val range = Range(565653, 565659)
        val result: Set[BigDecimal] = SillyIdFinder.findSillyIds(range)

        result should be(Set.empty)
      }
    }

    "given the 824824821-824824827 range" should {
      "return a non empty set" in new Fixture {
        val range = Range(824824821, 824824827)
        val result: Set[BigDecimal] = SillyIdFinder.findSillyIds(range)

        result should be(Set.empty)
      }
    }

    "given the 2121212118-2121212124 range" should {
      "return a non empty set" in new Fixture {
        val range = Range(2121212118, 2121212124)
        val result: Set[BigDecimal] = SillyIdFinder.findSillyIds(range)

        result should be(Set.empty)
      }
    }
  }
}
