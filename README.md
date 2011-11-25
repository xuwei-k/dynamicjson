# DynamicJSON

## A simple wrapper Scala Standard library JSON

inspired casbah-dynamic

https://github.com/mongodb/casbah/blob/r2.1.5.0_2.9.0-1/casbah-dynamic/src/main/scala/DynamicDBObject.scala

Scala runtime version require 2.9.0 or later . 
Because using `-Xexperimental` option and `scala.Dynamic` trait

## sxr

* main http://xuwei-k.github.com/dynamicjson/main/ 

* test http://xuwei-k.github.com/dynamicjson/test/

## sbt setting sample

```scala
scalaVersion := "2.9.1"

libraryDependencies += "com.github.xuwei-k" %% "dynamicjson" % "0.1" 

resolvers += "xuwei-k github" at "http://xuwei-k.github.com/mvn/"
```


