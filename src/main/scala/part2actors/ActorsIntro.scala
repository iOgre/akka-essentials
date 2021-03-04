package part2actors

import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorRef

object ActorsIntro extends App {

  //part 1 - actor system
  val actorSystem = ActorSystem("firstActorSystem")
  println(actorSystem.name)

  //part 2 - create actors

  class WordCountActor extends Actor {
    //internal data
    var totalWords = 0

    //behavior
    def receive: PartialFunction[Any, Unit] = {
      case message: String =>
        println(s"[word count] Received: $message")
        totalWords + message.split(" ").length
      case msg => println(s"[word count] I can't process ${msg.toString}")
    }
  }

  //part 3 - instantiate actor
  val wordCounter: ActorRef = actorSystem.actorOf(Props[WordCountActor], "wordCounter")

  //part 4 - communicate

  wordCounter ! "I am learning akka old way"

}
