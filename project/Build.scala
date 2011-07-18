import sbt._

object MyBuild extends Build{
  import Keys._

  lazy val sampleProject = Project("root",file(".")) 

  override lazy val settings = super.settings ++ Seq(
    version := "0.1-SNAPSHOT",
    organization := "xuwei_k",
    scalaVersion := "2.9.0-1",
    libraryDependencies ++= Seq(
      "org.specs2"  %% "specs2" % "1.5" % "test",
      "org.scalaj"  %% "scalaj-http" % "0.2.8" ,
      "net.liftweb" %% "lift-json" % "2.4-M2" ,
      "org.scala-tools.time" %% "time" % "0.4"
 //     "org.scalatra" %% "scalatra" % "2.0.0-SNAPSHOT"
    ),
    scalacOptions ++= Seq(
      "-deprecation",
      "-unchecked",
      "-Xexperimental"
    )
  )
}

// vim: set ts=2 sw=2 et:
