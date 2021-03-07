package part2actors

import akka.actor.Actor
import com.typesafe.scalalogging.Logger
import com.typesafe.scalalogging.StrictLogging
import akka.actor.ActorLogging
import akka.actor.ActorSystem
import akka.actor.Props

object ActorCapabilities extends App with StrictLogging {
  val system       = ActorSystem("actorCapabilitiesDemo")
  val simpleActior = system.actorOf(Props[SimpleActor], "simpleActor")
  logger.info("Actor capabilities")
  simpleActior ! "good day"
  system.terminate()
}

class SimpleActor extends Actor with ActorLogging {

  def receive: Receive = {
    case msg: String => log.info(s"I have received: $msg")
    case _           =>
  }
}
