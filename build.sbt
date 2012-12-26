name := "dynamicjson"

version := "0.1.1"

organization := "com.github.xuwei-k"

scalaVersion := "2.10.0"

libraryDependencies += "com.novocode" % "junit-interface" % "0.10-M2" % "test->default"

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-Xexperimental"
)

initialCommands in console := {
  "import dynamicJSON.Imports._"
}

resolvers += Opts.resolver.sonatypeReleases

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
