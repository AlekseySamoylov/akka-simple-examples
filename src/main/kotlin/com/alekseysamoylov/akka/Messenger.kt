package com.alekseysamoylov.akka

import akka.actor.AbstractLoggingActor
import akka.actor.ActorRef
import akka.actor.Props
import akka.pattern.Patterns
import akka.util.Timeout
import scala.concurrent.Await
import java.util.concurrent.TimeUnit


class Messenger(private val messageReceiver: ActorRef): AbstractLoggingActor() {
  override fun createReceive(): Receive {
    return receiveBuilder()
        .match(Message::class.java) { messageReceiver.tell(it, self()) }
        .match(GetCount::class.java) {
          println("Counter ${it.count}")
          sender().tell(it, self)

          val future2 = Patterns.ask(messageReceiver, Message("Count"), Timeout(1, TimeUnit.SECONDS))
          val reply = Await.result(future2, Timeout(2, TimeUnit.SECONDS).duration()) as GetCount
          println("Hello from Messenger actor ${reply.count} hi")
        }
        .build()
  }
}

fun messangerProps(messageReceiverActor: ActorRef): Props {
  return Props.create(Messenger::class.java) {
    Messenger(messageReceiverActor)
  }
}
