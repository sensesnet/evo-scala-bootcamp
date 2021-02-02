package com.evolution.bootcamp.task1

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest._
import matchers.should._
import com.evolution.bootcamp.basics._

class TestSpec extends AnyWordSpec with Matchers {

  "LCD val test" in {
    withClue("(2, 2) is NOT equal 2") {
      lcm(2, 2).get should equal(2)
    }
    withClue("(0, 10) is NOT equal 0") {
      lcm(0, 10) should equal(None)
    }
    withClue("(0, 0) is NOT equal 0") {
      lcm(0, 0) should equal(None)
    }
    withClue("(10, 0) is NOT equal 0") {
      lcm(10, 0) should equal(None)
    }
    withClue("(15, 18) is NOT equal 90") {
      lcm(15, 18).get should equal(90)
    }
    withClue("(-15, 18) is NOT equal 90") {
      lcm(-15, 18).get should equal(90)
    }
  }

  "GCD val test" in {
    withClue("(2, 2) is NOT equal 2") {
      gcd(2, 2).get should equal(2)
    }
    withClue("(0, 10) is NOT equal Error") {
      gcd(0, 10).get should equal(10)
    }
    withClue("(0, 0) is NOT equal Error") {
      gcd(0, 10).get should equal(10)
    }
    withClue("(10, 0) is NOT equal Error") {
      gcd(10, 0).get should equal(10)
    }
    withClue("(15, 18) is NOT equal 3") {
      gcd(15, 18).get should equal(3)
    }
  }
}
