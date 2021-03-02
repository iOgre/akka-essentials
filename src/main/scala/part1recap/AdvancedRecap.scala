package part1recap

import scala.concurrent.Future

object AdvancedRecap extends App {
  //partial functions

  val partialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 65 
    case 5 => 999
  }

  val pf = (x: Int) =>
    x match {
      case 1 => 42
      case 2 => 65
      case 5 => 999
    }

  val function: (Int => Int) = partialFunction

  val modifiedList = List(1, 2, 3).map {
    case 1 => 42
    case _ => 0
  }

  //lifting
  val lifted = partialFunction.lift
  println(lifted(2))
  println(lifted(44))

  // orElse
  val pfChain = partialFunction.orElse[Int, Int] { case 60 =>
    9000
  }

  println(pfChain(5))
  println(pfChain(60))
  //pfChain(456)

  //type alias

  type ReceiveFunction = PartialFunction[Any, Unit]

  def receive: ReceiveFunction = {
    case 1 => println("hello")
    case _ => println("confused")
  }

  //implicits

  implicit val timeout = 3000

  def setTimeout(f: () => Unit)(
    implicit
    tx: Int
  ) = f()

  setTimeout(() => println("timeout"))

  case class Person(name: String) {
    def greet = s"Hi, I am $name"
  }

  implicit def string2Person(name: String): Person = Person(name)

  println("bob".greet)

  implicit class Dog(name: String) {
    def bark = println("Woof")
  }

  "Lassie".bark

  //local scope
  implicit val invOrd: Ordering[Int] = Ordering.fromLessThan(_ > _)
  println(List(5, 7, 1).sorted)

  //imported scope
  import scala.concurrent.ExecutionContext.Implicits.global

  val fut = Future {
    println("hello, fuutre")
  }

}
