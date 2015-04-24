name := "sbt-java8"

version := "1.0"

resolvers += Resolver.url("xerial", url("http://repo1.maven.org/maven2/org/xerial/sbt/"))

scalaVersion := "2.10.4"

retrieveManaged := true

libraryDependencies += "com.google.guava" % "guava" % "18.0"
libraryDependencies += "com.esotericsoftware.yamlbeans" % "yamlbeans" % "1.09"