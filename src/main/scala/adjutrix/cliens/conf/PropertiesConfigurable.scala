package adjutrix.cliens.conf

import grizzled.slf4j.Logging
import java.util.Properties
import java.io._
import scala.Array

trait PropertiesConfigurable extends Configurable {
  implicit lazy val configuration = PropertiesConfiguration.loadOrCreateDefault()
}

object PropertiesConfiguration extends Logging {

  val defaultConfigurationFilePath = new File(Array(System.getProperty("user.home"), ".cliens", "configuration.properites").reduceLeft(_ + File.separator + _))

  def loadOrCreateDefault(usingFile: File = defaultConfigurationFilePath) = {
    debug("loading configuration..." + usingFile)
    if (usingFile.exists) {
      loadFromStream(new FileInputStream(usingFile))
    } else {
      loadFromDefaultAndSave(usingFile)
    }
  }

  def loadFromDefaultAndSave(toFile: File = defaultConfigurationFilePath) = save(loadFromDefault(), toFile)

  def loadFromDefault() = loadFromStream(classOf[Configuration].getResourceAsStream("/default.configuration.properties"))

  def loadFromStream(stream: InputStream) = {
    val properties = new Properties
    withCloseable[InputStream](() => stream, fis => properties.load(fis))
    new PropertiesConfiguration(properties)
  }

  def save(configuration: PropertiesConfiguration, toFile: File = defaultConfigurationFilePath) = {
    toFile.getParentFile.mkdirs()
    withCloseable[FileOutputStream](() => new FileOutputStream(toFile),
      fos => configuration.properties.store(fos, "Default properties"))
    configuration
  }

  private def withCloseable[T <: Closeable](create: () => T, operate: T => Unit) {
    var closeable: T = null.asInstanceOf[T]
    try {
      closeable = create()
      operate(closeable)
    } finally {
      if (closeable != null) {
        closeable.close()
      }
    }
  }
}

class PropertiesConfiguration(private val properties: Properties) extends Configuration with Logging {

  def url = properties.getProperty("url")

  def username = properties.getProperty("username")

  def password = properties.getProperty("password")
}