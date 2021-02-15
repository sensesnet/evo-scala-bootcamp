package com.evolution.bootcamp.task3

import enumeratum.{Enum, EnumEntry}

sealed abstract class MathPattern(val name: String, val pattern: String) extends EnumEntry

object MathPattern extends Enum[MathPattern] {

  case object Divide extends MathPattern("divide", "%s is %s")

  case object Sum extends MathPattern("sum", "the sum of %s is %s")

  case object Average extends MathPattern("average", "the average of %s is %s")

  case object Min extends MathPattern("min", "the minimum of %s is %s")

  case object Max extends MathPattern("max", "the maximum of %s is %s")

  val values: IndexedSeq[MathPattern] = findValues
}
