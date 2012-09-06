import Build._

name := "cliens"

version := "0.0.1"

organization := "Konstantin_Grigoriev"

scalaVersion := "2.9.2"

libraryDependencies ++= Seq(
  "net.iharder" % "base64" % "2.3.8",
  "com.github.scopt" %% "scopt" % "2.1.0",
  "com.codahale" % "jerkson_2.9.1" % "0.5.0",
  "org.clapper" %% "grizzled-slf4j" % "0.6.9",
  "ch.qos.logback" % "logback-classic" % "1.0.6",
  // tests
  "org.specs2" %% "specs2" % "1.11" % "it,test",
  "org.mockito" % "mockito-all" % "1.9.0" % "it,test",
  "junit" % "junit" % "4.10" % "it,test"
)

ivyScala ~= {
  _.map(_.copy(checkExplicit = false))
}

selectedProfile := Profile.dev

scalacOptions ++= Seq("-unchecked", "-deprecation")

seq(ScctPlugin.scctSettings: _*)

testOptions in Test += Tests.Argument("console", "junitxml")

testOptions in Build.IntegrationTest += Tests.Argument("console", "junitxml")

testOptions in Build.IntegrationTest <++= (name, selectedProfile) map {
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

resolvers += "repo.codahale.com" at "http://repo.codahale.com"

resolvers += "typesafe" at "http://repo.typesafe.com/typesafe/repo"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
