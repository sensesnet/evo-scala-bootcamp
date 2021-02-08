package com.evolution.bootcamp.task2

object Shape {

  import scala.math._

  sealed case class Point3D(cordX: Double = 0,
                            cordY: Double = 0,
                            cordZ: Double = 0) {
    def x = cordX

    def y = cordY

    def z = cordZ
  }

  // Add additional 2D shapes such as triangle and square.
  sealed trait Shape2D {
    def area(): Option[Double]
  }

  //3D shapes classes (origin, point, sphere, cube, cuboid, 3D triangle)
  sealed trait Shape3D {
    def surfaceArea: Option[Double]

    def volume: Option[Double]
  }

  sealed case class Triangle2D(pointA: Point3D, pointB: Point3D, pointC: Point3D) extends Shape2D {

    override def area(): Option[Double] = {
      Some(abs(
        ((pointA.x - pointC.x) * (pointB.y - pointC.y)) -
          ((pointA.y - pointC.y) * (pointB.x - pointC.x))
      ) / 2)
    }
  }

  sealed case class Square2D(pointA: Point3D, pointB: Point3D, pointC: Point3D, pointD: Point3D) extends Shape2D {

    override def area(): Option[Double] = {

      val lengthAB = sqrt(pow(pointB.x - pointA.x, 2) + pow(pointB.y - pointA.y, 2))
      val lengthAC = sqrt(pow(pointC.x - pointA.x, 2) + pow(pointC.y - pointA.y, 2))
      val lengthAD = sqrt(pow(pointD.x - pointA.x, 2) + pow(pointD.y - pointA.y, 2))

      val length = List(lengthAB, lengthAC, lengthAD).min

      List(lengthAB, lengthAC, lengthAD).count(_ == length) match {
        case 2 => Some(pow(length, 2))
        case _ => None
      }
    }
  }

  sealed case class Point(point: Point3D) extends Shape3D {

    override def volume: Option[Double] = None

    override def surfaceArea: Option[Double] = None
  }

  sealed case class Sphere(point: Point3D, radius: Double) extends Shape3D {

    override def volume: Option[Double] =
      Some(4 / 3 * Math.PI * Math.pow(radius, 3))

    override def surfaceArea: Option[Double] =
      Some(4 * Math.PI * Math.pow(radius, 2))
  }

  sealed case class Cube(point: Point3D, sideLength: Double) extends Shape3D {

    override def volume: Option[Double] = Some(Math.pow(sideLength, 3))

    override def surfaceArea: Option[Double] = Some(6 * Math.pow(sideLength, 2))
  }

  sealed case class Cuboid(point: Point3D,
                           width: Double,
                           height: Double,
                           length: Double
                          ) extends Shape3D {

    override def volume: Option[Double] = Some(height * length * width)

    override def surfaceArea: Option[Double] = Some(2 * Math.pow(width, 2) + 2 * Math.pow(height, 2) + 2 * Math.pow(length, 2))
  }

  sealed case class Triangle3D(pointA: Point3D,
                               pointB: Point3D,
                               pointC: Point3D,
                               pointD: Point3D)
    extends Shape3D {

    override def volume: Option[Double] = ???

    override def surfaceArea: Option[Double] = Some(
      Triangle2D(pointA, pointB, pointC).area().get +
        Triangle2D(pointB, pointC, pointD).area().get +
        Triangle2D(pointA, pointC, pointD).area().get +
        Triangle2D(pointA, pointB, pointD).area().get)

  }

}
