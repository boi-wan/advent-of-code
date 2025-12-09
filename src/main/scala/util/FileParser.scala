package com.paoloboi.adventofcode
package util

import scala.io.Source
import scala.util.{Try, Using}

class FileParser[A](rowParser: RowParser[A]) {

  def readFile(fileName: String): Try[List[A]] = {
    Using(Source.fromResource(fileName)) { source =>
      source.getLines().map(rowParser.parse).toList
    }
  }
}
