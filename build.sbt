name := "dynamicjson"

version := "0.1.1-SNAPSHOT"

organization := "com.github.xuwei-k"

scalaVersion := "2.9.1"

crossScalaVersions ++= Seq("2.10.0-M2")

libraryDependencies ++= Seq(
  "org.specs2"  % "specs2_2.9.1" % "1.8.2" % "test"
)


scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-Xexperimental"
)

//scalacOptions <+= scalaSource in Compile map { "-P:sxr:base-directory:" + _.getAbsolutePath }
//addCompilerPlugin("org.scala-tools.sxr" %% "sxr" % "0.2.8-SNAPSHOT")
initialCommands in console := {
  "import dynamicJSON.Imports._"
}

resolvers ++= Seq(
  "https://oss.sonatype.org/content/repositories/releases/"
).map{u => u at u}

/*
seq(lsSettings: _*)
(externalResolvers in LsKeys.lsync) := Seq(
  "xuwei-k github maven repository" at "http://xuwei-k.github.com/mvn/"
)
*/
publishTo := {
  sys.env.get("MAVEN_DIRECTORY").map{ dir =>
    Resolver.file("gh-pages",file(dir))(Patterns(true, Resolver.mavenStyleBasePattern))
  }
}
