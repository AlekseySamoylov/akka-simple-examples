package com.alekseysamoylov.akka

import akka.actor.AbstractActor
import akka.actor.Props
import akka.event.Logging


class Printer : AbstractActor() {
  private val log = Logging.getLogger(context.system, this)
  override fun createReceive(): Receive =
      receiveBuilder()
          .match(Greeting::class.java) {greeting -> log.info(greeting.message)}
          .build()
}

class Greeting(val message: String)

fun printerProps(): Props =
    Props.create(Printer::class.java) {Printer()}
