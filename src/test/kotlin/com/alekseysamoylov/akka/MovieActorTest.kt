package com.alekseysamoylov.akka

import akka.actor.ActorSystem
import akka.actor.Props
import akka.testkit.TestKit
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeUnit


class MovieActorTest {

  lateinit var system: ActorSystem

  @BeforeEach
  fun setup() {
    system = ActorSystem.create()
  }

  @AfterEach
  fun shutdown() {
    system.terminate()
  }

  @Test
  fun shouldGetMovieByName() {
    // Given
    val movieName = "starwars"
    val expectedResult = "Star Wars"
    val probe = TestKit(system)
    val props = Props.create(MovieActor::class.java)
    val movieActorRef = system.actorOf(props)

    // When
    movieActorRef.tell(movieName, probe.testActor())

    // Then
    probe.expectMsg(Duration.create(1, TimeUnit.SECONDS), expectedResult)
  }

  @Test
  fun shouldGetMovieByNameOnlyFiveTimes() {
    // Given
    val movieName = "starwars"
    val expectedResult = "Star Wars"
    val expectedResultExpired = "No"
    val probe = TestKit(system)
    val props = Props.create(MovieActor::class.java)
    val movieActorRef = system.actorOf(props)

    // When
    movieActorRef.tell(movieName, probe.testActor())
    movieActorRef.tell(movieName, probe.testActor())
    movieActorRef.tell(movieName, probe.testActor())
    movieActorRef.tell(movieName, probe.testActor())
    movieActorRef.tell(movieName, probe.testActor())
    movieActorRef.tell(movieName, probe.testActor())

    // Then
    probe.expectMsg(Duration.create(1, TimeUnit.SECONDS), expectedResult)
    probe.expectMsg(Duration.create(1, TimeUnit.SECONDS), expectedResult)
    probe.expectMsg(Duration.create(1, TimeUnit.SECONDS), expectedResult)
    probe.expectMsg(Duration.create(1, TimeUnit.SECONDS), expectedResult)
    probe.expectMsg(Duration.create(1, TimeUnit.SECONDS), expectedResult)
    probe.expectMsg(Duration.create(1, TimeUnit.SECONDS), expectedResultExpired)
  }
}
