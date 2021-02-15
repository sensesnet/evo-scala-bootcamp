package com.evolution.bootcamp.task3

import com.evolution.bootcamp.task3.ControlStructures.Command._

import scala.io.Source
import scala.util.Try

object ControlStructures {

  sealed trait Command

  object Command {

    final case class Divide(dividend: Double, divisor: Double) extends Command

    final case class Sum(numbers: List[Double]) extends Command

    final case class Average(numbers: List[Double]) extends Command

    final case class Min(numbers: List[Double]) extends Command

    final case class Max(numbers: List[Double]) extends Command

  }

  val unrecognizedData = "Error: unrecognized data"
  val invalidInputData = "Error: invalid data"

  final case class Result(operation: MathPattern, numbers: List[Double], value: Double)

  final case class ErrorMessage(value: String)

  def parseCommand(line: String): Either[ErrorMessage, Command] = {
    val command :: values = line.toLowerCase.split("\\s+").toList
    val numbers = values.map(cont => Try {
      cont.toDouble
    }.toOption)

    if (!numbers.forall(_.isDefined))
      Left(ErrorMessage("Error: Parse numbers error."))
    else
      (command, numbers) match {
        case (MathPattern.Divide.`name`, _ :: Nil) => Left(ErrorMessage("Error: Only Dividend"))
        case (MathPattern.Divide.`name`, None :: _ :: Nil) => Left(ErrorMessage("Error: Dividend can't be parsed."))
        case (MathPattern.Divide.`name`, _ :: None :: Nil) => Left(ErrorMessage("Error: Divisor can't be parsed."))
        case (MathPattern.Divide.`name`, x :: y :: Nil) => Right(Divide(x.get, y.get))
        case (MathPattern.Sum.`name`, _) => Right(Sum(numbers.map(_.get)))
        case (MathPattern.Average.`name`, _) => Right(Average(numbers.map(_.get)))
        case (MathPattern.Min.`name`, _) => Right(Min(numbers.map(_.get)))
        case (MathPattern.Max.`name`, _) => Right(Max(numbers.map(_.get)))
        case (_, _) => Left(ErrorMessage(s"$unrecognizedData"))
        case _ => Left(ErrorMessage(s"$invalidInputData"))
      }
  }


  def calculate(command: Command): Either[ErrorMessage, Result] =
    command match {
      case Divide(_, 0.0) => Left(ErrorMessage("Error: Divisor can't by zero. Non exist value."))
      case Divide(x, y) => Right(Result(MathPattern.Divide, List(x, y), x / y))
      case Sum(numbers) => Right(Result(MathPattern.Sum, numbers, numbers.sum))
      case Average(numbers) => Right(Result(MathPattern.Average, numbers, numbers.sum / numbers.size))
      case Min(numbers) => Right(Result(MathPattern.Min, numbers, numbers.min))
      case Max(numbers) => Right(Result(MathPattern.Max, numbers, numbers.max))
    }

  def renderResult(result: Result): String =
    result.operation match {
      case MathPattern.Divide =>
        result.operation.pattern.format(result.numbers.map(_.toString).mkString("", " divided by ", ""), result.value)
      case _ =>
        result.operation.pattern.format(result.numbers.map(_.toString).mkString("", " ", ""), result.value)
    }

  def process(input: String): String = {
    (for {
      command <- parseCommand(input)
      result <- calculate(command)
    } yield renderResult(result)).left.map(_.value).merge
  }

  def main(args: Array[String]): Unit = Source.stdin.getLines() map process foreach println
}
