name := "dynamicjson"

version := "0.1.2-SNAPSHOT"

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

licenses := Seq("MIT" -> url("http://opensource.org/licenses/MIT"))

homepage := Some(url("https://github.com/xuwei-k/dynamicjson"))

publishTo := {
  sys.env.get("MAVEN_DIRECTORY").map{ dir =>
    Resolver.file("gh-pages",file(dir))(Patterns(true, Resolver.mavenStyleBasePattern))
  }
}
