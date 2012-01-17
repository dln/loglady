organization := "org.eintr.loglady"

name := "loglady"

version := "1.0.0"

description := "Crazy simple logging."

scalaVersion := "2.9.1"

crossScalaVersions := Seq("2.8.1", "2.8.2", "2.9.0-1", "2.9.1")

libraryDependencies := Seq(
  "org.slf4j"      %  "slf4j-api"       % "1.6.1",
  "ch.qos.logback" %  "logback-classic" % "1.0.0" % "test",
  "junit"          %  "junit"           % "4.10"  % "test",
  "org.specs2"     %% "specs2"          % "1.5" % "test"
)

resolvers += ScalaToolsSnapshots

scalacOptions ++= Seq("-optimize", "-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8")

cancelable := true

testOptions in Test += Tests.Argument("console", "junitxml")
