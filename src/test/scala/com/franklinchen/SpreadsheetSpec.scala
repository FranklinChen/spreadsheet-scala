package com.franklinchen

import org.scalatest._
import flatspec._
import matchers._

// To use Scala for-comprehensions for monads.
import cats.syntax.functor._
import cats.syntax.flatMap._

abstract class SpreadsheetSpec extends AnyFlatSpec with should.Matchers {
  val spreadsheet: Spreadsheet
  import spreadsheet._
  import spreadsheet.expMonad.pure

  "A Spreadsheet" should "Handle dependencies in 3-cell graph" in {
    val threeCells: Exp[(Cell[Int], Cell[Int], Cell[Int])] = for {
      a <- cell(pure(1))

      b <- cell(pure(2))

      c <- cell(
        for {
          aValue <- get(a)
          bValue <- get(b)
        } yield aValue + bValue
      )
    } yield (a, b, c)

    val (a, b, c) = spreadsheet.run(threeCells)

    spreadsheet.run(get(c)) should be (3)

    set(a, pure(100))
    spreadsheet.run(get(c)) should be (102)

    set(a, for {
      bValue <- get(b)
    } yield bValue * bValue)
    set(b, pure(4))

    spreadsheet.run(get(c)) should be (20)
  }

  it should "Handle dependencies among different cell types" in {
    val differentTypesCells: Exp[(Cell[String], Cell[Int], Cell[Int])] = for {
      a <- cell(pure("hello"))

      b <- cell(pure(2))

      c <- cell(
        for {
          aValue <- get(a)
          bValue <- get(b)
        } yield aValue.length + bValue
      )
    } yield (a, b, c)

    val (a, b, c) = spreadsheet.run(differentTypesCells)

    spreadsheet.run(get(c)) should be (7)

    set(b, pure(3))
    set(a, pure("no"))

    spreadsheet.run(get(c)) should be (5)
  }
}
