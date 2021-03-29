package com.evolution.bootcamp.task5.poker.exception

object ExceptionMessages {

  val errorPrefix = "Error: "
  val wrongBoardCard = s"$errorPrefix Wrong boards cards list: "
  val wrongHandCard = s"$errorPrefix Wrong hand combination: "
  val invalidInputData = s"$errorPrefix Invalid input data: "
  val invalidGameData = s"$errorPrefix Invalid game data: "
  val unrecognizedGameData = s"$errorPrefix Unrecognized game type"
  val wrongAnalyzeGameData = s"$errorPrefix Wrong game analyze"
  val wrongParseCard = s"$errorPrefix Wrong parse card:"
  val emptyHandCardList = s"$errorPrefix Empty hand card list"
  val wrongNumberOfPlayers = s"$errorPrefix Wrong number of players: "
  val duplicateCardData = s"$errorPrefix Duplicate card data:"
}

