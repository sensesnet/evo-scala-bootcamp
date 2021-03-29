package com.evolution.bootcamp.task5

object adt {

  sealed trait Suit
  case object Spades   extends Suit
  case object Hearts   extends Suit
  case object Diamonds extends Suit
  case object Clubs    extends Suit

}
