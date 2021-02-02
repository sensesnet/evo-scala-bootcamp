package com.evolution.bootcamp

import scala.annotation.tailrec

package object basics {

  def lcm(a: Int, b: Int): Option[Int] =
    (a, b) match {
      case (0, _) => None
      case (_, 0) => None
      case (a, b) => Some(math.abs(a * b) / gcd(b, a % b).get)
    }

  @tailrec
  def gcd(a: Int, b: Int): Option[Int] =
    (a, b) match {
      case (0, 0) => None
      case (a, 0) => Some(a)
      case (0, b) => Some(b)
      case (a, b) => gcd(b, a % b)
    }
}
