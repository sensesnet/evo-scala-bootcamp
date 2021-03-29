package com.evolution.bootcamp.task4

import scala.annotation.tailrec

object Mutability {

  import scala.collection.mutable

  // scala has both mutable and immutable collections.
  // immutable versions imported by default and you need to explicitly specify you want mutable version.

  // val vs var, mutable immutable
  // something which is obvious for java programmers but may be different in some other languages

  // immutable map
  val m1: Map[Int, String] = Map(1 -> "a", 2 -> "b")
  m1 + (3 -> "c") // returns a new collection

  // map is immutable but you can assign a new map to m2
  var m2: Map[Int, String] = Map(1 -> "a", 2 -> "b")
  m2 = Map(1 -> "a")

  // map is mutable but you cannot assign a new map to m3
  val m3: mutable.Map[Int, String] = mutable.Map(1 -> "a")
  m3 += (2 -> "b")

  // can both be reassigned and changed
  var m4: mutable.Map[Int, String] = mutable.Map(1 -> "a")
  m4 += (2 -> "b")
  m4 = mutable.Map(1 -> "a")
}

object MutabilityAndRt extends {
  // in functional programming it is ok to use a mutable collection if it never leaks from a function

  def mkString[T](l: List[T]): String = {
    val a = new StringBuilder() // mutable thing
    l.foreach(x => a.append(x)) // mutation
    a.result()
  }

  // from the outside function is pure so you can do this but avoid if you can
}

// (!mutable!) is a Scala's representation for Java's T[]
object JavaArray extends App {
  val a = Array(1, 2, 3)
  a(2) = 4 // can be mutated, not super cool
  println(a.toList)
}

// immutable array alternative
object CoolArray extends App {
  val a = Vector(1, 2, 3) // this cool array is in fact a tree
  // a(2) = 4  --  will not compile
  val b = a.updated(2, 4) // cool immutable collection with performant random reads and updates
  println(b)
}

object Sets extends App {
  val beautifulNumber = Set(1, 3, 5, 7, 123) // they are not special in any way, just beautiful to me at this particular moment

  // the same as contains
  println(beautifulNumber(3))
  println(beautifulNumber(4))
  println(beautifulNumber(1))
}

object Maps extends App {
  val map = Map(1 -> "a", 2 -> "b")
  val map2 = Map((1, "a"), (2, "b")) // same map
  println(map(2)) // gets element and throws exception
  println(map.get(3)) // gets element safely
  println(map)
  println(map.withDefaultValue("lalala")(3)) // uses default
  println(map)
  println(map.withDefaultValue("lalala").get(3)) // shows only real elements
}

object LinkedList extends App {

  /*
    Linked lists work great for functional programming:
      - it is efficient in memory
      - it is quick to add to the beginning
      - cool pattern matching
   */

  // list is recursive
  object MyList {

    sealed trait List[+T]

    object Empty extends List[Nothing]

    case class NonEmpty[T](head: T, tail: List[T]) extends List[T]

    NonEmpty(1, NonEmpty(2, NonEmpty(3, Empty)))
  }

  val list = List(1, 2, 3, 4, 5, 6)
  val list2 = List("a", "b", "c", "d", "e")

  // collections nice methods
  println(list.head)
  println(list.tail)
  println(list.init)
  println(list.last)

  println(list.headOption)
  println(list.lastOption)
  println(list.minOption)
  println(list.maxOption)

  println(list.sum)
  println(list.product)
  println(list.reverse)
  println(list.distinct)
  println(list.indices)

  println(list.sorted) // implicit ordering
  println(list.sortBy(-_))
  println(list.sortWith { (a, b) => a > b })

  // there will be a separate lecture on implicits but lets have a look now
  object ImplicitParams {

    case class User(id: Int)

    val l: List[User] = List(User(1), User(3), User(2))
    l.sortBy(_.id)
    l.sortWith { (u1, u2) => u1.id < u2.id }

    implicit val ord: Ordering[User] = Ordering.by(_.id) // sorted won't compile if we don't provide an Ordering for it
    l.sorted
  }

  // they are the same
  println(list.drop(2).take(2))
  println(list.slice(2, 4))

  println(list.take(2).intersect(list.take(5)))

  println(list.contains(2))
  println(list.exists(_ % 2 == 0))
  println(list.count(_ % 2 == 0))
  println(list.find(_ % 2 == 0))
  println(list.filter(_ % 2 == 0))
  println(list.filterNot(_ % 2 == 0))
  val (trues, elses) = list.partition(_ % 2 == 0) // returns two lists
  println(list.forall(_ % 2 == 0)) // returns true on empty

  println(list.groupBy(_ / 5))

  println(list.map(_ * 2))
  println(list.flatMap(x => List(x, x)))
  println(List(list, list, list).flatten)

  // Zips
  println(list.zipWithIndex) // == list.zip(list.indices)
  println(list zip list.tail)
  println(list zip list2)

  /*
     In a sorted list find two numbers which have a gap between
        None for List(1, 2, 3, 4)
        Some((2, 8)) for List(1, 2, 8)
   */
  @tailrec
  def findGap(l: List[Int]): Option[(Int, Int)] = {
    l match {
      case Nil => None
      case _ :: Nil => None
      case head :: next :: _ if (head + 1) != next => Some(head, next)
      case _ :: _ :: tail => findGap(tail)
    }
  }

  // recursion
  def sum(list: List[Int]): Int = list match {
    case h :: tail => h + sum(tail)
    case Nil => 0
  }

  // folds
  list.foldLeft(0) { (acc, x) => acc + x }
  list.foldRight(0) { (acc, x) => acc + x }
  list.fold(0) { (acc, x) => acc + x }
  list.reduce(_ + _)
  list.reduceLeft(_ + _)
  list.reduceRight(_ + _)

  // try to implement min different ways (fold, reduce, recursion)
  def minViaReduce(list: List[Int]): Option[Int] =
    list match {
      case Nil => None
      case _ => list.reduceLeftOption(_ min _)
    }

  def minViaFold(list: List[Int]): Option[Int] =
    list match {
      case Nil => None
      case _ => list.reduceLeftOption(_ min _)
    }

  def minViaRecursion(list: List[Int]): Option[Int] = {

    @tailrec
    def min(list: List[Int], currentMin: Int = Int.MaxValue): Option[Int] = {
      if (list.isEmpty) Some(currentMin)
      else if (list.head < currentMin) min(list.tail, list.head)
      else min(list.tail, currentMin)
    }

    if (list == Nil) None
    else
      min(list)
  }

  // Implement scanLeft (not using scans ofc)
  def scanLeft[T](zero: T)(list: List[T])(f: (T, T) => T): List[T] =
    list.foldLeft(zero :: Nil) { (l, v) =>
      f(l.head, v) :: l
    }.reverse

  // https://twitter.com/allenholub/status/1357115515672555520/photo/1
  // pass the interview
  def count(s: String): List[(Char, Int)] = {
    s.foldRight[List[(Char, Int)]](Nil) {
      case (next, (current, n) :: xs) if current == next => (next, n + 1) :: xs
      case (next, result) => (next, 1) :: result
    }
  }

  // hometask:
  // https://leetcode.com/problems/running-sum-of-1d-array/
  def runningSum(nums: Array[Int]): Array[Int] =
    nums.scanLeft(0) { (acc, i) => acc + i }.tail

  // https://leetcode.com/problems/shuffle-the-array
  def shuffle(nums: Array[Int], n: Int): Array[Int] = {
    val (x, y) = nums.splitAt(n)
    (x zip y).flatMap(i => List(i._1, i._2))
  }

  // https://leetcode.com/problems/richest-customer-wealth
  def maximumWealth(accounts: Array[Array[Int]]): Int =
    accounts.map(_.sum).max

  // https://leetcode.com/problems/kids-with-the-greatest-number-of-candies/
  def kidsWithCandies(candies: Array[Int], extraCandies: Int): Array[Boolean] = {
    val max = candies.max
    candies.map(_ + extraCandies >= max)
  }

  // https://leetcode.com/problems/widest-vertical-area-between-two-points-containing-no-points
  def maxWidthOfVerticalArea(data: Array[Array[Int]]): Int = {
    val points = data.map(line => (line(0), line(1)))
    val temp = points.map(_._1).sorted
    temp.zip(temp.tail).map(p => p._2 - p._1).max
  }

  // https://leetcode.com/problems/maximum-nesting-depth-of-the-parentheses/
  def maxDepth(s: String): Int = {
    val onlyQuotes = s.filter(s => s == '(' || s == ')')
    onlyQuotes.scanLeft(0) { case (acc, el) =>
      if (el == '(') acc + 1 else if (el == ')') acc - 1 else acc
    }.max
  }

  // https://leetcode.com/problems/split-a-string-in-balanced-strings
  def balancedStringSplit(s: String): Int =
    s.scanLeft(0) { case (acc, el) =>
      if (el == 'R') acc + 1 else if (el == 'L') acc - 1 else acc
    }.max * 2


  // https://leetcode.com/problems/matrix-block-sum/
  def matrixBlockSum(matrix: Array[Array[Int]], k: Int): Array[Array[Int]] = {
    def sum(i: Int, j: Int): Int =
      (for {
        v <- (i - k) to (i + k) if matrix.isDefinedAt(v)
        h <- (j - k) to (j + k) if matrix(v).isDefinedAt(h)
      } yield matrix(v)(h)).sum

    val result = Array.ofDim[Int](matrix.length, matrix.map(_.length).maxOption.getOrElse(0))

    for {
      i <- matrix.indices
      j <- matrix(i).indices
    } result(i)(j) = sum(i, j)

    result
  }

  /*
    Additional information:

    In some other functional languages foldRight (Haskell) is lazy and works on Infinite collections.
    Haskell foldRight can be found in scalaz/cats.
    See: http://voidmainargs.blogspot.com/2011/08/folding-stream-with-scala.html
   */

  object WhyTwoParamLists {
    def map1[A, B](l: List[A], f: A => B): List[B] = l map f

    //    map1(List(1, 2, 3), x => x + 1) // compiler cannot understand what type is x
    map1(List(1, 2, 3), (x: Int) => x + 1) // so you have to tell the type

    def map2[A, B](l: List[A])(f: A => B): List[B] = l map f

    map2(List(1, 2, 3))(x => x + 1) // and now compiler is smarter
    map2(List(1, 2, 3))(_ + 1) // so you can even do this
  }

  object TheFourDots { // ! additional information which can be skipped !
    // a method on list which ends in : so it is associates to right
    1 :: 2 :: 3 :: Nil

    // a two param case class
    list match {
      case Nil =>
      case head :: tail =>
    }

    list match {
      case Nil =>
      case ::(head, tail) =>
    }

    case class MultipliedBy(a: Int, b: Int)

    MultipliedBy(5, 6) match {
      case a MultipliedBy b => a * b
    }

    // all this is very fun but nobody really does this (RIP scalaz \/)
    val map1: String Map Int = Map("a" -> 1)
    val map2: Map[String, Int] = Map("a" -> 1)

    // but you don't need to care just use :: and have fun
  }

}

object PassingManyParams extends App {

  // Syntax for many params
  def method[T](ts: T*): Unit = println(ts.head)

  // correct usage
  method(1, 2, 3)

  // passes seq of lists
  val list = List(1, 2, 3)
  method(list)

  // correct usage
  method(list: _*)
}

// Lets get back to [+T]
object Variance {
  /*
      +------------------------+------------------------+
      |       MyType[+T]       |       MyType[-T]       |
      +------------------------+------------------------+
      | A -> B                 | A -> B                 |
      | MyType[A] -> MyType[B] | MyType[A] <- MyType[B] |
      +------------------------+------------------------+

    Lets say Int is a Number

    List has + because
    if I want List[Number] I can have List[Int] instead

    JsonPrinter (it converts a type to json) would have - because
    if I want JsonPrinter[Int] I can have JsonPrinter[Number] as it can print my Int as well as any other Number
   */

  /*
    Function is -A => +B
    if i need a function which returns Numbers I am ok with functions returning Int since Int is a Number
    if i need a function which processes Ints I am ok with functions processing any Number
   */

  type Color

  class Animal

  class Cat extends Animal

  val describe: Animal => Color = ???
  val describeCat: Cat => Color = describe // if you can describe any animals then you can describe cats as well

  val createCat: Color => Cat = ???
  val create: Color => Animal = createCat // if you want to create a red animal then creating red cat fits your needs
}
