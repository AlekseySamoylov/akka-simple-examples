package com.alekseysamoylov.akka

import akka.actor.AbstractActor


class MovieActor : AbstractActor() {
  private var counter = 0;
  override fun createReceive(): Receive {
    return receiveBuilder()
        .match(String::class.java) {
          if (it == "starwars") {
            if (counter < 5) {
              counter++
              sender.tell("Star Wars", self())
            } else {
              sender.tell("No", self())
            }
          } else {
            sender.tell("another movie", self())
          }
        }
        .build()
  }
}
