name := "cliens"

version := "0.0.1"

organization := "Konstantin_Grigoriev"

scalaVersion := "2.8.1"

// Add multiple dependencies
libraryDependencies ++= Seq(
    "commons-codec" % "commons-codec" % "1.4",
    "org.scala-tools.testing" %% "specs" % "1.6.6" % "test",
    "org.mockito" % "mockito-all" % "1.8.5" % "test"
)

parallelExecution in Test := false

