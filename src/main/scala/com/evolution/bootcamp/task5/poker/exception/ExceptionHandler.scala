package com.evolution.bootcamp.task5.poker.exception

object ExceptionHandler {

  final case class TexasHoldemPokerException(message: String) extends RuntimeException(message)

  final case class OmahaHoldemPokerException(message: String) extends RuntimeException(message)

  final case class FiveCardDrawPokerException(message: String) extends RuntimeException(message)

  final case class CardParserException(message: String) extends RuntimeException(message)

  final case class GameAnalyzeException(message: String) extends RuntimeException(message)
}
