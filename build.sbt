version := "0.1-SNAPSHOT"

organization := "com.github.xuwei_k"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "org.specs2"  %% "specs2" % "1.6.1" % "test"
)

resolvers ++= Seq(
  "xuwei-k repository" at "http://xuwei-k.github.com/mvn"
)

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-Xexperimental"
)

addCompilerPlugin("org.scala-tools.sxr" % "sxr_2.9.1" % "0.2.8-SNAPSHOT")

initialCommands in console := {
  "import dynamicJSON._"
}

