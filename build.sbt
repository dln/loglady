organization := "org.eintr.loglady"

name := "loglady"

version := "1.0.1-SNAPSHOT"

description := "Crazy simple logging API for Scala."

licenses := Seq("Apache License, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))

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

// Publishing

publishMavenStyle := true

publishArtifact in Test := false

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT")) Some("snapshots" at nexus + "content/repositories/snapshots")
  else                             Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

pomIncludeRepository := { x => false }

pomExtra := (
  <url>http://github.com/dln/loglady</url>
  <licenses>
    <license>
      <name>Apache License, Version 2.0</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:dln/loglady.git</url>
    <connection>scm:git:git@github.com:dln/loglady.git</connection>
  </scm>
  <developers>
    <developer>
      <id>dln</id>
      <name>Daniel Lundin</name>
      <url>http://eintr.org/</url>
    </developer>
  </developers>)

seq(lsSettings :_*)

LsKeys.tags in LsKeys.lsync := Seq("log", "logging", "slf4j")

externalResolvers in LsKeys.lsync := Nil

site in LsKeys.lsync := "http://github.com/dln/loglady/"

