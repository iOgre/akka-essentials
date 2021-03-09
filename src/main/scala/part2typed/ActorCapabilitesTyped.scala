package part2typed

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.ActorSystem
import SimpleActor._
import akka.actor.typed.scaladsl.ActorContext

object ActorCapabilitesTyped extends App {
  val simple = ActorSystem(SimpleActor(), "simple")
  val another = ActorSystem(SimpleActor(), "another")
  simple ! TypeA
  simple ! TypeC("hello")
  another ! TypeB
  simple.terminate()
  another.terminate()
}

object SimpleActor {
    def apply() = Behaviors.receive[Message] {
        (ctx: ActorContext[Message], msg) =>
        ctx.log.warn(s"${ctx.self}")
        msg match {
            case TypeA => ctx.log.info("Received type A")
            case TypeB => 
            ctx.log.info("Received type B")
            ctx.self ! TypeC("self!")
            case TypeC(tx) => ctx.log.info(s"Received type C with ${tx} content")
        }
        Behaviors.same
    }
    sealed trait Message
    case object TypeA extends Message
    case object TypeB extends Message
    case class TypeC(tx:String) extends Message
}