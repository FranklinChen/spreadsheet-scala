package com.franklinchen

import org.specs2._

// To use Scala for-comprehensions for monads.
import cats.syntax.functor._
import cats.syntax.flatMap._

abstract class SpreadsheetSpec extends Specification { def is = s2"""
  ${`Handle dependencies in 3-cell graph`}
  ${`Handle dependencies among different cell types`}
  """

  val spreadsheet: Spreadsheet
  import spreadsheet._
  import spreadsheet.expMonad.pure

  def `Handle dependencies in 3-cell graph` = {
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

    val (a, b, c) = run(threeCells)

    run(get(c)) ==== 3

    set(a, pure(100))
    run(get(c)) ==== 102

    set(a, for {
      bValue <- get(b)
    } yield bValue * bValue)
    set(b, pure(4))

    run(get(c)) ==== 20
  }

  def `Handle dependencies among different cell types` = {
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

    val (a, b, c) = run(differentTypesCells)

    run(get(c)) ==== 7

    set(b, pure(3))
    set(a, pure("no"))

    run(get(c)) ==== 5
  }
}
