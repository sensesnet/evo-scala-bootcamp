package com.evolution.bootcamp.task5.poker.util

import enumeratum._

sealed abstract class Suit(val identifier: String) extends EnumEntry

object Suit extends Enum[Suit] {

  case object s extends Suit("Spades")

  case object h extends Suit("Hearts")

  case object d extends Suit("Diamonds")

  case object c extends Suit("Clubs")

  val values: IndexedSeq[Suit] = findValues

}
