package part2actors

import akka.actor.Actor
import com.typesafe.scalalogging.Logger
import com.typesafe.scalalogging.StrictLogging
import akka.actor.ActorLogging
import akka.actor.ActorSystem
import akka.actor.Props
import SimpleActor._
import akka.actor.ActorRef

object ActorCapabilities extends App with StrictLogging {
  val system      = ActorSystem("actorCapabilitiesDemo")
  val simpleActor = system.actorOf(Props[SimpleActor], "simpleActor")

  logger.info("Actor capabilities")
  simpleActor ! "good day"
  //1 messages can be of any type
  //a) message must be immutable
  //b) messages must be serializable
  // in practice, use case classes & case objects
  simpleActor ! 9
  simpleActor ! SpecialMessage("some content")

  //2 actors have info about their context and themselves
  simpleActor ! SelfMessage("I am an actor")

  system.terminate()
}

//3 actors can reply to messages
object ActorReply extends App {
  val system = ActorSystem("ReplyDemo")

  val alice = system.actorOf(Props[SimpleActor], "alice")
  val bob   = system.actorOf(Props[SimpleActor], "bob")

  
  alice ! SayHiTo(bob, "Hi!")

  //4 dead letters (sender)
  alice ! "Hi!"
  system.terminate()

}

//3.a - cycle!
object ActorInfiniteReply extends App {
  val system = ActorSystem("ReplyDemo")

  val alice = system.actorOf(Props[SimpleActor], "alice")
  val bob   = system.actorOf(Props[SimpleActor], "bob")

  alice ! TalkTo(bob, "good day!")
 
}

//5 forward message
//d -> a -> b


class SimpleActor extends Actor with ActorLogging {

  //context.self equivalent this in OOP

  def receive: Receive = {
    case "Hi!" => 
    log.warning(s"Hi received from ${context.sender().path}")
    context.sender() ! "Hello there"
    case msg: String        => log.info(s"${context.self.path} I have received: $msg")
    case n: Int             => log.info(s"Received NUMBER $n, n*n=${n * n}")
    case sp: SpecialMessage => log.info(s"Received special message: $sp")
    case SelfMessage(ct)    => self ! ct
    case SayHiTo(ref, msg)       => 
    log.warning(s"I am going to say ${msg} to ${ref.path}:")
    ref ! msg
    case TalkTo(ref, msg) =>
    log.warning(s"I am going to talk to ${ref.path} about ${msg} :")
    ref ! TalkTo(self, s"You told me that ${msg}")
    case _                  =>
  }
}

object SimpleActor {
  case class SpecialMessage(contents: String)
  case class SelfMessage(ctx: String)
  case class SayHiTo(ref: ActorRef, message:String)
  case class TalkTo(ref:ActorRef, message:String)
  case class WirelessPhone(content:String, )
}
