package com.evolution.bootcamp.task5.poker.util

import com.evolution.bootcamp.task5.poker.exception.ExceptionHandler._
import com.evolution.bootcamp.task5.poker.util.Card
import com.evolution.bootcamp.task5.poker.exception.ExceptionMessages._
import com.evolution.bootcamp.task5.poker.util.CardParser._

object GameDataAnalyzer {

  private def checkBoardCard(board: List[Card], numOfCard: Int): Boolean =
    board.length == numOfCard

  private def checkHandCard(hand: List[Card], numOfCard: Int): Boolean =
    hand.length == numOfCard

  private def checkNumOfPlayers(hands: List[List[Card]], min: Int, max: Int): Boolean =
    min <= hands.length & hands.length <= max

  private def checkCardDuplicate(board: List[Card], hands: List[List[Card]]): Boolean = {
    val gameCards = board ::: hands.flatten
    gameCards.length == gameCards.toSet.size
  }

  private def findDuplicateCard(board: List[Card], hands: List[List[Card]]): String = {
    decodeCards((board ::: hands.flatten) diff (board ::: hands.flatten).distinct)
  }

  def analyzeTexasHoldemData(board: List[Card], hands: List[List[Card]]): Unit = {
    if (!checkBoardCard(board, 5)) throw TexasHoldemPokerException(s"$wrongBoardCard ${board.toString}")
    if (hands.isEmpty) throw TexasHoldemPokerException(emptyHandCardList)
    if (!checkNumOfPlayers(hands, 2, 25)) throw TexasHoldemPokerException(s"$wrongNumberOfPlayers ${hands.length}")
    if (!checkCardDuplicate(board, hands)) throw TexasHoldemPokerException(s"$duplicateCardData ${findDuplicateCard(board, hands)}")
    hands.foreach(hand =>
      if (!checkHandCard(hand, 2)) throw TexasHoldemPokerException(s"$wrongHandCard ${hand.toString}")
    )
  }

  def analyzeOmahaHoldemData(board: List[Card], hands: List[List[Card]]): Unit = {
    if (!checkBoardCard(board, 5)) throw OmahaHoldemPokerException(s"$wrongBoardCard ${board.toString}")
    if (hands.isEmpty) throw OmahaHoldemPokerException(emptyHandCardList)
    if (!checkNumOfPlayers(hands, 2, 12)) throw OmahaHoldemPokerException(s"$wrongNumberOfPlayers ${hands.length}")
    if (!checkCardDuplicate(board, hands)) throw OmahaHoldemPokerException(s"$duplicateCardData ${findDuplicateCard(board, hands)}")
    hands.foreach(hand =>
      if (!checkHandCard(hand, 4)) throw OmahaHoldemPokerException(s"$wrongHandCard ${hand.toString}")
    )
  }

  def analyzeFiveCardDrawData(board: List[Card], hands: List[List[Card]]): Unit = {
    if (!checkBoardCard(board, 5)) throw FiveCardDrawPokerException(s"$wrongBoardCard ${board.toString}")
    if (hands.isEmpty) throw FiveCardDrawPokerException(emptyHandCardList)
    if (!checkNumOfPlayers(hands, 2, 10)) throw FiveCardDrawPokerException(s"$wrongNumberOfPlayers ${hands.length}")
    if (!checkCardDuplicate(board, hands)) throw FiveCardDrawPokerException(s"$duplicateCardData ${findDuplicateCard(board, hands)}")
    hands.foreach(hand =>
      if (!checkHandCard(hand, 5)) throw FiveCardDrawPokerException(s"$wrongHandCard ${hand.toString}")
    )
  }
}
