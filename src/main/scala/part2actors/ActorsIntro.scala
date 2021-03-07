package part2actors

import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorRef
import akka.actor.ActorLogging
import com.typesafe.scalalogging.StrictLogging

object ActorsIntro extends App with StrictLogging {

  //part 1 - actor system
  val actorSystem = ActorSystem("firstActorSystem")
  
  logger.info(actorSystem.name)

  //part 2 - create actors

  class WordCountActor extends Actor with ActorLogging {
    //internal data
    var totalWords = 0

    //behavior
    def receive: Receive = {
      case message: String =>
        log.info(s"Received: $message")
        totalWords + message.split(" ").length
      case msg => log.info(s"I can't process ${msg.toString}")
    }
  }

  //part 3 - instantiate actor

  val wordCounter: ActorRef        = actorSystem.actorOf(Props[WordCountActor], "wordCounter")
  val anotherWordCounter: ActorRef = actorSystem.actorOf(Props[WordCountActor], "anotherWordCounter")

  //part 4 - communicate

  wordCounter ! "I am learning akka old way"
  anotherWordCounter ! "I got the message"
  //  val personActor = actorSystem.actorOf(Props(new Person("Bob"))) // <-- discouraged, use companion object approach
  val personActor = actorSystem.actorOf(Person.props("Bobby"), "personx") // <-- use this way
  actorSystem.terminate()

}

class Person(name: String) extends Actor with ActorLogging {

  def receive: Receive = {
    case "hi" => log.info(s"hi, I am $name")
    case _    =>
  }
}

object Person {
  def props(name: String) = Props(new Person(name))
}
