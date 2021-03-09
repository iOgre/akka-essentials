package part2typed

import akka.actor.typed.ActorSystem
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors
import com.typesafe.scalalogging.StrictLogging
import scala.concurrent.ExecutionContext.Implicits.global
object ActorsIntroTyped extends App with StrictLogging {
val counter: ActorSystem[String]  = ActorSystem(ActorCounter(),"counter")
counter ! "what is this really?"
}

object ActorCounter {

  def apply(): Behavior[String] = Behaviors.receive { (ctx, msg) =>
    ctx.log.info(s"Received: $msg")
    Behaviors.same
  }

}
