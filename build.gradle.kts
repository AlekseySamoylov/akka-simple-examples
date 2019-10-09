import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.2.71"
}

group = "com.alekseysamoylov.investor"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
  mavenCentral()
}

dependencies {

  implementation("com.typesafe.akka:akka-actor_2.13:2.6.0-M8")
  implementation("com.typesafe.akka:akka-slf4j_2.13:2.6.0-M8")
  implementation("com.typesafe.akka:akka-stream_2.13:2.6.0-M8")
  implementation("com.typesafe.akka:akka-http-jackson_2.13:10.1.10")
  implementation("com.typesafe.akka:akka-http-core_2.13:2.6.0-M8")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  testImplementation("com.typesafe.akka:akka-http-testkit_2.13:10.1.10")
  testImplementation("com.typesafe.akka:akka-testkit_2.13:2.6.0-M8")
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.2")
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "1.8"
  }
}

tasks.withType<Test> {
  exclude("com/alekseysamoylov/reactor/fluxandmonoplayground")
  useJUnitPlatform()
}

tasks.withType<Wrapper> {
  gradleVersion = "5.5"
}
