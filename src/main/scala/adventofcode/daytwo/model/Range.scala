package adventofcode.daytwo.model

case class Range(head: BigDecimal, tail: BigDecimal) {
  override def toString: String = s"$head-$tail"
}
