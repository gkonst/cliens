import Build._

name := "cliens"

version := "0.0.1"

organization := "Konstantin_Grigoriev"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "commons-codec" % "commons-codec" % "1.6",
  "org.scala-tools.testing" %% "specs" % "1.6.9" % "it,test",
  "org.specs2" %% "specs2" % "1.8.2" % "it,test",
  "org.mockito" % "mockito-all" % "1.9.0" % "it,test"
)

selectedProfile := Profile.dev

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
        println("current profile : " + profile)
      }))
    }
}

parallelExecution in Test := false
