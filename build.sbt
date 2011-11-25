name := "DynamicJSON"

version := "0.1"

organization := "com.github.xuwei-k"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "org.specs2"  %% "specs2" % "1.6.1" % "test"
)

scalacOptions <+= scalaSource in Compile map { "-P:sxr:base-directory:" + _.getAbsolutePath }

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-Xexperimental"
)

addCompilerPlugin("org.scala-tools.sxr" % "sxr_2.9.0" % "0.2.7")

initialCommands in console := {
  "import dynamicJSON.Imports._"
}

seq(lsSettings: _*)

(externalResolvers in LsKeys.lsync) := Seq(
  "xuwei-k github maven repository" at "http://xuwei-k.github.com/mvn/"
)
