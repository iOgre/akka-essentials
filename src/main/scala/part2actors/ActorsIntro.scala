package part2actors

import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorRef
import akka.actor.ActorLogging
import akka.event.Logging

object ActorsIntro extends App {

  //part 1 - actor system
  val actorSystem = ActorSystem("firstActorSystem")
  println(actorSystem.name)

  //part 2 - create actors

  class WordCountActor extends Actor {
    //internal data
    var totalWords = 0
    val log        = Logging(context.system, this)

    //behavior
    def receive: Receive = {
      case message: String =>
        log.info(s"[word count] Received: $message")
        totalWords + message.split(" ").length
      case msg => log.info(s"[word count] I can't process ${msg.toString}")
    }
  }

  //part 3 - instantiate actor

  val wordCounter: ActorRef        = actorSystem.actorOf(Props[WordCountActor], "wordCounter")
  val anotherWordCounter: ActorRef = actorSystem.actorOf(Props[WordCountActor], "anotherWordCounter")

  //part 4 - communicate

  wordCounter ! "I am learning akka old way"
  anotherWordCounter ! "I got the message"

  actorSystem.terminate()

  // val personActor = actorSystem.actorOf(Props(new Person("bob")))
}

class Person(name: String) extends Actor {
  def receive: Receive = ???
}
