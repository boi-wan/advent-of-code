package adventofcode.daythree.model

case class Joltage(value: BigDecimal) extends AnyVal {
  def +(other: Joltage): Joltage = Joltage(this.value + other.value)
}
