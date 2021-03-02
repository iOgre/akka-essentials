package part1recap

import scala.util.Try

object GeneralRecap extends App {
  val aCondition: Boolean = false
  var aVariable           = 42
  aVariable += 4

  val aConditionedVal = if (aCondition) 42 else 65

  val aCodeBlock = {
    if (aCondition) 74
    else 56
  }

  // val theUnit = println(s"Hello, scala $aCodeBlock")

  def aFunction(x: Int) = x + 1

  def fact(n: Int, acc: Int): Int =
    if (n <= 0) acc
    else fact(n - 1, acc * n)

  class Animal

  trait Carnivore {
    def eat(a: Animal): Unit
  }
  class Sheep extends Animal

  class Dog extends Animal with Carnivore {
    override def eat(a: Animal): Unit = println(s"GNAWL $a")
  }

  class Crocodile extends Animal with Carnivore {

    override def eat(a: Animal): Unit = println(s"CRUNCH $a")

  }
  val aDog: Dog = new Dog
  val aSheep    = new Sheep
  val aCroc     = new Crocodile

  aCroc.eat(aSheep)
  aDog.eat(aSheep)

  val aCarnivore = new Carnivore {
    def eat(a: Animal): Unit = println(s"Nyam $a")
  }

  aCarnivore eat aCroc

  abstract class MyList[+A]
  object MyList

  case class Person(name: String, age: Int)

  val aPotentialFailure: Unit =
    try throw new RuntimeException("I am innocent")
    catch {
      case e: Exception => println("I caught an exception!")
    } finally println(" bunch of logs")

  val incrementer = new Function1[Int, Int] {
    def apply(v1: Int): Int = v1 + 1
  }

  val incrm = incrementer(9)
  //eqv incrementer.apply(9)

  println(incrm)

  val anonymousIncrementer = (x: Int) => x + 1

  List(1, 4, 6).map(l => println(anonymousIncrementer(l)))

  val anOption = Some(3)

  val aTry = Try {
    throw new RuntimeException("rwerr")
  }

  val unknown = 2
  val order = unknown match {
      case 1 => "first"
      case 2 => "second"
      case _ => "?"
  }

  println(order)

  val bob = Person("Bob", 22)
  val greet = bob match {
      case Person(name, _) => s"Hi, my name is $name"
      case _ => "Hi, i don't know whomai"
  }

  println(greet)
}
