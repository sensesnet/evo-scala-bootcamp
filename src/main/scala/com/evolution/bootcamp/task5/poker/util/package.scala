package com.evolution.bootcamp.task5.poker

import com.evolution.bootcamp.task5.poker.util.{Suit, Weight}

package object util {

  case class Card(weight: Weight,
                  suit: Suit)

  case class PokerHand(combination: String,
                       hand: List[Card],
                       score: Int,
                       combinationCardScore: Int = 0,
                       kickerCardScore: Int = 0)

  case class Result(hand: List[Card],
                    combination: String,
                    score: Int,
                    combinationCardScore: Int = 0,
                    kickerCardScore: Int = 0)
}
