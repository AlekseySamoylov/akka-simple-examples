package com.alekseysamoylov.akka

import akka.actor.AbstractLoggingActor
import akka.actor.Props
import java.lang.RuntimeException


class MessageReceiver() : AbstractLoggingActor() {
  private var counter: Int = 0
  override fun createReceive(): Receive {
    return receiveBuilder()
        .match(Message::class.java) { message ->
          log().info(message.text + " $counter")
          if (message.text == "Count") {
            sender().tell(GetCount(counter), self())
          } else {
            counter++
          }
          if (message.text == "Stop") {
            context.stop(sender)
          }
        }
        .build()
  }
}

fun receiverProps(): Props = Props.create(MessageReceiver::class.java) { MessageReceiver() }
