import sbt._

object MyBuild extends Build{
  import Keys._

  lazy val sampleProject = Project("root",file(".")) 

  override lazy val settings = super.settings ++ Seq(
    version := "0.1-SNAPSHOT",
    organization := "xuwei_k",
    scalaVersion := "2.9.1",
    libraryDependencies ++= Seq(
      "org.specs2"  %% "specs2" % "1.5" % "test"
    ),
    scalacOptions ++= Seq(
      "-deprecation",
      "-unchecked",
      "-Xexperimental"
    )
    ,addCompilerPlugin("org.scala-tools.sxr" % "sxr_2.9.0" % "0.2.7")
  )
}

