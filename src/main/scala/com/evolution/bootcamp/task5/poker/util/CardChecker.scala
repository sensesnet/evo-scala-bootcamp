package com.evolution.bootcamp.task5.poker.util

import com.evolution.bootcamp.task5.poker.util.{Card, PokerHand}

import scala.annotation.tailrec

object CardChecker {

  def getScore(combo: List[Card], hand: List[Card]): PokerHand = {
    val combination: List[Card] = combo ::: hand
    combination match {
      case _ if isStraightFlush(combination)._1 =>
        PokerHand(
          "Straight Flush", hand, 800, isStraightFlush(combination)._2)
      case _ if isFourOfAKind(combination)._1 =>
        PokerHand(
          "Four of a Kind", hand, 700, isFourOfAKind(combination)._2, isFourOfAKind(combination)._3)
      case _ if isFullHouse(combination)._1 =>
        PokerHand(
          "Full House", hand, 600, isFullHouse(combination)._2, isFullHouse(combination)._3)
      case _ if isFlush(combination)._1 =>
        PokerHand(
          "Flush", hand, 500, isFlush(combination)._2)
      case _ if isStraight(combination)._1 =>
        PokerHand(
          "Straight", hand, 400, isStraight(combination)._2)
      case _ if isThreeOfAKind(combination)._1 =>
        PokerHand(
          "Three of a Kind", hand, 300, isThreeOfAKind(combination)._2, isThreeOfAKind(combination)._3)
      case _ if isTwoPairs(combination)._1 =>
        PokerHand(
          "Two Pairs", hand, 200, isTwoPairs(combination)._2, isTwoPairs(combination)._3)
      case _ if isPair(combination)._1 =>
        PokerHand(
          "Pair", hand, 100, isPair(combination)._2, isPair(combination)._3)
      case _ =>
        PokerHand(
          "High Card", hand, combination.maxBy(_.weight.rank).weight.rank, spareCardScore(combination, List(combination.maxBy(_.weight.rank))))
    }
  }

  @tailrec
  private def isStraightFlush(combination: List[Card]): (Boolean, Int) = {

    val sortedCards = combination.map(_.weight.rank).sorted

    if (sortedCards.length < 5)
      (false, 0)

    else if (
      sortedCards
        .takeRight(5)
        .sliding(2)
        .forall(pair => pair.head == pair(1) - 1) && isFlush(combination.take(5))._1)
      (true, sortedCards.takeRight(5).sum)

    else
      isStraightFlush(combination.sortBy(_.weight.rank).dropRight(1))
  }

  private def isFourOfAKind(combination: List[Card]): (Boolean, Int, Int) = {

    val quad = combination.groupBy(_.weight.rank).filter {
      case (_, cards) => cards.length == 4
    }

    if (quad.size == 1)
      (true, quad.head._2.map(_.weight.rank).sum, spareCardScore(combination, quad.head._2))
    else
      (false, 0, 0)
  }

  private def isFullHouse(combination: List[Card]): (Boolean, Int, Int) = {

    val triples = combination.groupBy(_.weight.rank).filter {
      case (_, cards) => cards.length == 3
    }

    val pairs = combination.groupBy(_.weight.rank).filter {
      case (_, cards) => cards.length >= 2
    }

    if (triples.nonEmpty && pairs.size > triples.size || (triples.size == 2 && pairs.size == 2)) {
      val tripleScore = triples.toList.maxBy(_._1)._1
      val pairScore = (pairs.toList.map(_._1) diff List(tripleScore)).max
      (true, tripleScore, pairScore)
    }
    else
      (false, 0, 0)
  }

  private def isFlush(combination: List[Card]): (Boolean, Int) = {

    val flush = combination.groupBy(_.suit).filter {
      case (_, cards) => cards.length >= 5
    }

    if (flush.size == 1)
      (true, flush.head._2.map(_.weight.rank).sum)
    else
      (false, 0)
  }

  @tailrec
  private def isStraight(combination: List[Card]): (Boolean, Int) = {

    val sortedCards = combination.map(_.weight.rank).sorted

    if (sortedCards.length < 5)
      (false, 0)
    else if (sortedCards.takeRight(5).sliding(2).forall(pair => pair.head == pair(1) - 1))
      (true, sortedCards.takeRight(5).sum)
    else
      isStraight(combination.sortBy(_.weight.rank).dropRight(1))
  }

  private def isThreeOfAKind(combination: List[Card]): (Boolean, Int, Int) = {

    val triple = combination.groupBy(_.weight.rank).filter {
      case (_, cards) => cards.length == 3
    }

    if (triple.size == 1)
      (true, triple.head._2.map(_.weight.rank).sum, spareCardScore(combination, triple.head._2))
    else
      (false, 0, 0)
  }

  private def isTwoPairs(combination: List[Card]): (Boolean, Int, Int) = {

    val pairs = combination.groupBy(_.weight.rank).filter {
      case (_, cards) => cards.length == 2
    }

    if (pairs.size >= 2) {
      val pairScore = pairs.keys.max
      (true, pairScore, spareCardScore(combination, pairs.toList.sortBy(_._1).take(2).flatMap(_._2)))
    }
    else
      (false, 0, 0)
  }

  private def isPair(combination: List[Card]): (Boolean, Int, Int) = {
    val pair = combination.groupBy(_.weight.rank).filter {
      case (_, cards) => cards.length == 2
    }
    if (pair.size == 1)
      (true, pair.head._2.map(_.weight.rank).sum, spareCardScore(combination, pair.head._2))
    else
      (false, 0, 0)
  }

  private def spareCardScore(cards: List[Card], combo: List[Card]): Int = {
    cards.diff(combo).map(_.weight.rank).sorted.takeRight(5 - combo.length).sum
  }
}
