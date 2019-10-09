package com.alekseysamoylov.akka

import akka.actor.ActorRef
import akka.http.javadsl.model.StatusCodes
import akka.http.javadsl.server.HttpApp
import akka.http.javadsl.server.PathMatchers
import akka.http.javadsl.server.PathMatchers.segment
import akka.http.javadsl.server.Route
import akka.pattern.Patterns
import akka.util.Timeout
import scala.concurrent.Await
import java.util.concurrent.TimeUnit


class MovieRouter(val movieActor: ActorRef): HttpApp() {
  override fun routes(): Route {
    return path(PathMatchers.segment("movie").slash(segment())) { movieName: String -> route(getMovie(movieName))}
  }

  private fun getMovie(name: String): Route {
    return get() {
      val futureAnswer = Patterns.ask(movieActor, name, Timeout(1, TimeUnit.SECONDS))
      val answer = Await.result(futureAnswer, Timeout(1, TimeUnit.SECONDS).duration()) as String
      complete(StatusCodes.CREATED, answer)
    }
  }
}
