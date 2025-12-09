package com.paoloboi.adventofcode
package util

trait RowParser[A] {
  def parse(row: String): A
}

