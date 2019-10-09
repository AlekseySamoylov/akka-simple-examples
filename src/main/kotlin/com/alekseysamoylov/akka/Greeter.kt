package com.alekseysamoylov.akka

import akka.actor.AbstractActor
import akka.actor.ActorRef
import akka.actor.Props


class Greeter(private val message: String, private val printerActor: ActorRef, var greeting: String = "") : AbstractActor() {
  override fun createReceive(): Receive =
      receiveBuilder()
        .match(WhoToGreet::class.java) {whoToGreet -> greeting = "$message, ${whoToGreet.who}"}
        .match(Greet::class.java) { printerActor.tell(Greeting(greeting), self) }
        .build()
}

class WhoToGreet(val who: String)

class Greet()

fun greeterProps(message: String, printerActor: ActorRef): Props =
    Props.create(Greeter::class.java) { Greeter(message, printerActor) }
