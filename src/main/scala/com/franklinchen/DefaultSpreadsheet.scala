package com.franklinchen

import scalaz.Monad

object DefaultSpreadsheet extends Spreadsheet {
  /**
    A cell with global identity.

    Note: we could have used Scala's object identity eq, instead of our
    own globally uniquely generated id.
    */
  case class Cell[A](
    var code: Exp[A],
    var value: Option[A],
    var reads: List[ECell],
    var observers: List[ECell],
    id: Int
  )

  type Exp[A] = () => (A, List[ECell])

  /** Existential type. */
  type ECell = Cell[_]

  def id(c: ECell): Int = c.id

  /** Union of two lists. Should really be using Set. */
  def union(
    xs: List[ECell],
    ys: List[ECell]
  ): List[ECell] = xs match {
    case Nil => ys
    case x :: xs1 =>
      if (ys.exists(y => id(x) == id(y)))
        union(xs1, ys)
      else
        x :: union(xs1, ys)
  }

  implicit object expMonad extends Monad[Exp] {
    override def point[A](a: => A) = () =>
    (a, List())

    override def bind[A, B](e: Exp[A])(f: A => Exp[B]): Exp[B] = () => {
      val (a, cs) = e()
      val (b, ds) = f(a)()
      (b, union(cs, ds))
    }
  }

  var r: Int = 0

  def newId(): Int = {
    r += 1
    r
  }

  override def cell[A](e: Exp[A]): Exp[Cell[A]] = () => {
    val n = newId()
    val c = Cell(
      code = e,
      value = None,
      reads = List(),
      observers = List(),
      id = n
    )
    (c, List())
  }

  override def get[A](c: Cell[A]): Exp[A] = () =>
    c.value match {
      case Some(v) => (v, List(c))
      case None => {
        val (v, ds) = c.code()
        c.value = Some(v)
        c.reads = ds
        for (d <- ds) {
          d.observers = c :: d.observers
        }
        (v, List(c))
      }
    }

  /** Remove o from c's observers. */
  def removeObserver(o: ECell)(c: ECell): Unit =
    c.observers = c.observers filter { o1 => id(o) != id(o1) }

  def invalidate(c: ECell): Unit = {
    val os = c.observers
    val rs = c.reads

    c.observers = List()
    c.value = None
    c.reads = List()

    rs.foreach(removeObserver(c))
    os.foreach(invalidate)
  }

  override def set[A](c: Cell[A], e: Exp[A]): Unit = {
    c.code = e
    invalidate(c)
  }

  override def run[A](e: Exp[A]): A = e()._1
}
