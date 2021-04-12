package com.evolution.bootcamp.task5

object adt {

  type Weight = Int
  val Weight: Range = 2 to 14

  sealed trait Suit
  case object Spades extends Suit
  case object Hearts extends Suit
  case object Diamonds extends Suit
  case object Clubs extends Suit

  sealed abstract case class Card private(suit: Suit, weight: Weight)

  object Card {
    def apply(suit: Suit, weight: Weight): Option[Card] =
      if (Weight.contains(weight)) Some(new Card(suit, weight) {}) else None
  }

  sealed case class PokerHand(hand: List[Card])

  sealed case class Result(combination: String,
                           hand: PokerHand,
                           score: Int,
                           combinationCardScore: Int = 0,
                           kickerCardScore: Int = 0)
}
