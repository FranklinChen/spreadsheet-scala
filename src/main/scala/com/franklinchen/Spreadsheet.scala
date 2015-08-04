package com.franklinchen

import scala.language.higherKinds
import cats.Monad

trait Spreadsheet {
  type Cell[_]
  type Exp[_]

  implicit val expMonad: Monad[Exp]

  def cell[A](e: Exp[A]): Exp[Cell[A]]

  def get[A](c: Cell[A]): Exp[A]
  def set[A](c: Cell[A], e: Exp[A]): Unit

  def run[A](e: Exp[A]): A
}
