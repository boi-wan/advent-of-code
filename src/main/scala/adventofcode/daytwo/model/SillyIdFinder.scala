package adventofcode.daytwo.model

import scala.annotation.tailrec

object SillyIdFinder {
  def findSillyIds(range: Range): Set[BigDecimal] = {

    @tailrec
    def findIds(value: BigDecimal)(acc: Set[BigDecimal]): Set[BigDecimal] = {
      val nextAcc = acc ++ (if (isSilly(value)) Set(value) else Nil)

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
    if (isEven(value.toString.length)) {
      val (head, tail): (String, String) = value.toString.splitAt(value.toString.length / 2)
      head.contentEquals(tail)
    } else false
  }

  private def isEven(num: BigDecimal): Boolean = num % 2 == 0
}
