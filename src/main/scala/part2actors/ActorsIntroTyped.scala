package part2actor

import akka.actor.typed.ActorSystem
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors


object ActorsIntroTyped extends App {
    val firstCounter: ActorSystem[String] = ActorSystem(ActorCounter(), "wordCounter")
    val secondCounter: ActorSystem[String] = ActorSystem(ActorCounter(), "anotherWordCounter")
    firstCounter ! "heloo my dear"
    secondCounter ! "heloo my dear again"
    firstCounter.terminate()
    secondCounter.terminate()
}

class ActorCounter(ctx: ActorContext[String]) extends AbstractBehavior[String](ctx) {

  def onMessage(msg: String): Behavior[String] = {
   // ctx.log.info(s"[typed actor][${ctx.system.name}] Input: $msg")
    ctx.log.info(s"$msg")
    val length = msg.split(" ").length
    this
  }
}

object ActorCounter {
    def apply():Behavior[String] = Behaviors.setup(ct => new ActorCounter(ct))
}
