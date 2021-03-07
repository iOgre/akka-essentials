name := "akka-essentials"

version := "0.1"

scalaVersion := "2.13.5"

val akkaVersion      = "2.6.13"
val scalaTestVersion = "3.2.5"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor"   % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-typed"   % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "org.scalatest"     %% "scalatest"    % scalaTestVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
  
  
)
