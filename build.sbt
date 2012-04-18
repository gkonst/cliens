import Build._

name := "cliens"

version := "0.0.1"

organization := "Konstantin_Grigoriev"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "net.iharder" % "base64" % "2.3.8",
  "com.github.scopt" %% "scopt" % "2.0.0",
  "net.liftweb" %% "lift-json" % "2.4",
  "org.clapper" %% "grizzled-slf4j" % "0.6.8",
  "ch.qos.logback" % "logback-classic" % "1.0.1",
  // tests
  "org.scala-tools.testing" %% "specs" % "1.6.9" % "it,test",
  "org.specs2" %% "specs2" % "1.9" % "it,test",
  "org.mockito" % "mockito-all" % "1.9.0" % "it,test",
  "junit" % "junit" % "4.10" % "it,test"
)

selectedProfile := Profile.dev

scalacOptions ++= Seq("-unchecked", "-deprecation")

//seq(ScctPlugin.scctSettings: _*)

testOptions in Test += Tests.Argument("console", "junitxml")

testOptions in IntegrationTest += Tests.Argument("console", "junitxml")

testOptions in IntegrationTest <++= (name, selectedProfile) map {
  (n, profile) =>
    profile match {
      case Some(Profile.ci) => Seq(
        Tests.Setup(() => {
          "sh setup_it.sh".!
        }),
        Tests.Cleanup(() => {
          "sh cleanup_it.sh".!
        }))
      case _ => Seq(Tests.Setup(() => {
        printf("current profile : %s", profile)
      }))
    }
}

parallelExecution in Test := false
