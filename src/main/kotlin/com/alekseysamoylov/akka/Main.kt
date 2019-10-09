package com.alekseysamoylov.akka

import akka.actor.*
import akka.pattern.Patterns
import akka.util.Timeout
import java.util.concurrent.TimeUnit
import akka.actor.Nobody.path
import akka.dispatch.OnComplete
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import akka.actor.Nobody.path


class Main {
}


fun main(args: Array<String>) {
//  val system = ActorSystem.create("StopableSystem")
//  val receiverActorRef = system.actorOf(receiverProps())
//  val messagerActorRef = system.actorOf(messangerProps(
//      receiverActorRef
//  ))
//
//  for (i in 1 .. 10)
//  messagerActorRef.tell(Message("Hello2"), ActorRef.noSender())
//  messagerActorRef.tell(Message("Count"), ActorRef.noSender())
//  messagerActorRef.tell(Message("Hello5"), ActorRef.noSender())
//
//  messagerActorRef.tell(Message("Hello6"), ActorRef.noSender())
//  messagerActorRef.tell(Message("Hello2"), ActorRef.noSender())
//  Thread.sleep(4000)
//  messagerActorRef.tell(Message("Stop"), ActorRef.noSender())
//  messagerActorRef.tell(Message("Hello3"), ActorRef.noSender())
//  system.terminate()

  val system = ActorSystem.create("MovieSystem")
  val props = Props.create(MovieActor::class.java)
  val movieActorRef = system.actorOf(props)

  val movieRouter = MovieRouter(movieActorRef)

  movieRouter.startServer("localhost", 8080, system)
}
