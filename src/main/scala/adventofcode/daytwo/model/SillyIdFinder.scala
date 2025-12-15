package adventofcode.daytwo.model

import scala.annotation.tailrec

object SillyIdFinder {

  def findSillyIdsPart1(range: Range): Set[BigDecimal] = findSillyIds(range)(isSilly)

  def findSillyIdsPart2(range: Range): Set[BigDecimal] = findSillyIds(range)(isSillyEnhanced)

  private def findSillyIds(range: Range)(isSillyF: BigDecimal => Boolean): Set[BigDecimal] = {

    @tailrec
    def findIds(value: BigDecimal)(acc: Set[BigDecimal]): Set[BigDecimal] = {
      val nextAcc = acc ++ (if (isSillyF(value)) Set(value) else Nil)

      nextValue(value) match {
        case Some(next) => findIds(next)(nextAcc)
        case None => nextAcc
      }
    }

    def nextValue(value: BigDecimal): Option[BigDecimal] = {
      val next = value + 1
      if (next > range.tail) None else Some(next)
    }

    findIds(range.head)(Set.empty)
  }

  private def isSilly(value: BigDecimal): Boolean = {
    // Check if the string representation can be split in half and both halves are equal
    if (isEven(value.toString.length)) {
      val (head, tail): (String, String) = value.toString.splitAt(value.toString.length / 2)
      head.contentEquals(tail)
    } else false
  }

  private def isSillyEnhanced(value: BigDecimal): Boolean = {
    // Split the string in half and check all possible slide sizes in the first half
    val halfSplit: (String, String) = value.toString.splitAt(value.toString.length / 2)

    // For each possible size, check if the string is made up of repeated segments of that size
    (1 to halfSplit.head.length).foldLeft(false) {
      case (isSilly: Boolean, size: Int) =>
        // split the string into segments of the given size
        val slideBySize: List[String] = value.toString().grouped(size).toList

        slideBySize.tail.foldLeft(true) {
          case (isSillyInt: Boolean, slide: String) =>
            isSillyInt && slide.contentEquals(slideBySize.head)
        } || isSilly
    }
  }

  private def isEven(num: BigDecimal): Boolean = num % 2 == 0
}
