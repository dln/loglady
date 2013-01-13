organization := "org.eintr.loglady"

name := "loglady"

version := "1.1.0"

description := "Crazy simple logging API for Scala."

licenses := Seq("Apache License, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))

scalaVersion := "2.9.2"

crossScalaVersions := Seq("2.9.1", "2.9.2", "2.10.0")

libraryDependencies ++= Seq(
  "org.slf4j"      %  "slf4j-api"       % "1.7.2",
  "ch.qos.logback" %  "logback-classic" % "1.0.0" % "test",
  "junit"          %  "junit"           % "4.11"  % "test",
  "org.specs2"     %% "specs2"          % "1.12.3" % "test"
)

scalacOptions ++= Seq("-optimize", "-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8")

cancelable := true

testOptions in Test += Tests.Argument("console", "junitxml")

// Publishing

publishMavenStyle := true

publishArtifact in Test := false

publishTo <<= version { v =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT")) {
    Some("snapshots" at nexus + "content/repositories/snapshots")
  } else {
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
  }
}

pomIncludeRepository := { x => false }

pomExtra := (
  <url>http://github.com/dln/loglady</url>
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

//seq(lsSettings :_*)

//LsKeys.tags in LsKeys.lsync := Seq("log", "logging", "slf4j")

//externalResolvers in LsKeys.lsync := Nil

//LsKeys.site in LsKeys.lsync := "http://github.com/dln/loglady/"

