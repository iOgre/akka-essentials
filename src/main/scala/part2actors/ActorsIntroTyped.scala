package part2actor

import akka.actor.typed.ActorSystem
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors
import com.typesafe.scalalogging.StrictLogging

object ActorsIntroTyped extends App {
  val firstCounter: ActorSystem[String]  = ActorSystem(ActorCounter("wordCounter"), "wordCounter")
  val secondCounter: ActorSystem[String] = ActorSystem(ActorCounter("uff"), "wordCounter")
  firstCounter ! "Hello my dear"
  secondCounter ! "Hello my dear again"
  firstCounter.terminate()
  secondCounter.terminate()
}

class ActorCounter(ctx: ActorContext[String], name: String) extends AbstractBehavior[String](ctx) {

  def onMessage(msg: String): Behavior[String] = {
    ctx.log.info(s"[typed actor ~$name][${ctx.system.name}] Input: $msg")
    val length = msg.split(" ").length
    Behaviors.same
  }
}

object ActorCounter {
  def apply(name: String): Behavior[String] = Behaviors.setup(ct => new ActorCounter(ct, name))
}
