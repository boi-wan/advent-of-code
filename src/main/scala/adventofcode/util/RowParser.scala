package adventofcode.util

trait RowParser[A] {
  def parse(row: String): A
}
