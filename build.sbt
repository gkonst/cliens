name := "cliens"

version := "0.0.1"

organization := "Konstantin_Grigoriev"

scalaVersion := "2.9.1"

// Add multiple dependencies
libraryDependencies ++= Seq(
    "commons-codec" % "commons-codec" % "1.6",
    "org.scala-tools.testing" %% "specs" % "1.6.9" % "it,test",
    "org.mockito" % "mockito-all" % "1.9.0" % "it,test"
)


testOptions in IntegrationTest += Tests.Setup( () => {
    "sh setup_it.sh" !
})

testOptions in IntegrationTest += Tests.Cleanup( () => {
    "sh cleanup_it.sh" !
})

parallelExecution in Test := false

