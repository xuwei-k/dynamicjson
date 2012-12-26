lsSettings

(LsKeys.tags in LsKeys.lsync) := Seq("json")

(LsKeys.ghUser in LsKeys.lsync) := Some("xuwei-k")

(LsKeys.ghRepo in LsKeys.lsync) := Some("dynamicjson")

(externalResolvers in LsKeys.lsync) := Seq(
  "xuwei-k github maven repository" at "http://xuwei-k.github.com/mvn/"
)
