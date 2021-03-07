package part2actors

import akka.actor.Actor
import com.typesafe.scalalogging.Logger
import com.typesafe.scalalogging.StrictLogging
import akka.actor.ActorLogging
import akka.actor.ActorSystem
import akka.actor.Props

object ActorCapabilities extends App with StrictLogging {
  val system       = ActorSystem("actorCapabilitiesDemo")
  val simpleActor = system.actorOf(Props[SimpleActor], "simpleActor")
  logger.info("Actor capabilities")
  simpleActor ! "good day"
  //messages can be of any type
  simpleActor ! 9
  system.terminate()
}

class SimpleActor extends Actor with ActorLogging {

  def receive: Receive = {
    case msg: String => log.info(s"I have received: $msg")
    case n: Int      => log.info(s"Received NUMBER $n, n*n=${n * n}")
    case _           =>
  }
}
