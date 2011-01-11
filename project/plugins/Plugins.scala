import sbt._

class Plugins(info: ProjectInfo) extends PluginDefinition(info) {
    val resfilt = "com.bumnetworks.resfilt" % "resfilt" % "0.0.1"
    val scctRepo = "scct-repo" at "http://mtkopone.github.com/scct/maven-repo/"
    lazy val scctPlugin = "reaktor" % "sbt-scct-for-2.8" % "0.1-SNAPSHOT"
    val junitXmlrepo = "Christoph's Maven Repo" at "http://maven.henkelmann.eu/"
    val junitXml = "eu.henkelmann" % "junit_xml_listener" % "0.1"
}

