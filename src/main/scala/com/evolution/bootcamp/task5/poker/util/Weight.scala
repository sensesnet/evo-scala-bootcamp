package com.evolution.bootcamp.task5.poker.util

import enumeratum._

sealed abstract class Weight(val identifier: String, val rank: Int) extends EnumEntry

object Weight extends Enum[Weight] {

  case object Card_2 extends Weight("2", 2)

  case object Card_3 extends Weight("3", 3)

  case object Card_4 extends Weight("4", 4)

  case object Card_5 extends Weight("5", 5)

  case object Card_6 extends Weight("6", 6)

  case object Card_7 extends Weight("7", 7)

  case object Card_8 extends Weight("8", 8)

  case object Card_9 extends Weight("9", 9)

  case object Card_T extends Weight("T", 10)

  case object Card_J extends Weight("J", 11)

  case object Card_Q extends Weight("Q", 12)

  case object Card_K extends Weight("K", 13)

  case object Card_A extends Weight("A", 14)

  val values: IndexedSeq[Weight] = findValues
}
