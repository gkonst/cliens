import sbt._
import Keys._

object Build extends Build {
  lazy val root =
    Project("root", file("."))
      .configs( IntegrationTest )
      .settings( Defaults.itSettings : _*)

}
