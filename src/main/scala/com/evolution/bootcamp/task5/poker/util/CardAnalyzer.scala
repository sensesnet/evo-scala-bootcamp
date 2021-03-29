package com.evolution.bootcamp.task5.poker.util

import com.evolution.bootcamp.task5.poker.exception.ExceptionHandler.GameAnalyzeException
import com.evolution.bootcamp.task5.poker.util.{Card, Result}

object CardAnalyzer {

  def analyzeGame(board: List[Card], hands: List[List[Card]]): String = {
    try {
      getResult(board, hands)
        .map(comb => comb.hand)
        .map(handStr => CardParser.decodeCards(handStr))
        .mkString(" ")
    } catch {
      case e: Exception => throw GameAnalyzeException(e.getMessage)
    }
  }

  def getResult(board: List[Card], hands: List[List[Card]]): List[Result] = {
    val results = hands.map {
      hand =>
        val scoreCheck = CardChecker.getScore(board, hand)
        Result(
          hand,
          scoreCheck.combination,
          scoreCheck.score,
          scoreCheck.combinationCardScore,
          scoreCheck.kickerCardScore
        )
    }
    results.sortBy(_.kickerCardScore).sortBy(_.combinationCardScore).sortBy(_.score)
  }
}
