package adjutrix.cliens.conf

import java.util.Properties
import grizzled.slf4j.Logging
import java.io._

/**
 * Configuration model class, used to store and transfer configuration related values.
 *
 * @author konstantin_grigoriev
 */
case class Configuration(url: String,
                         username: String,
                         password: String)

/**
 * Configuration object, used as factory for configurations. Can load or save configurations.
 *
 * @author konstantin_grigoriev
 */
object Configuration extends Logging {
  val DIRECTORY = ".cliens"
  val PROPERTIES = "configuration.properties"
  var baseDir = System.getProperty("user.home")
  lazy val file = new File(Array(baseDir, DIRECTORY, PROPERTIES).reduceLeft(_ + File.separator + _))

  def load = {
    debug("loading configuration..." + file)
    if (!file.exists) {
      createDefault()
    }
    val properties = new Properties
    withCloseable[FileInputStream](() => new FileInputStream(file), fis => properties.load(fis))
    Configuration(properties.getProperty("url"), properties.getProperty("username"),
      properties.getProperty("password"))
  }

  def createDefault() {
    val properties = new Properties
    withCloseable[InputStream](() => classOf[Configuration].getResourceAsStream("/default.configuration.properties"),
      fis => properties.load(fis))
    file.getParentFile.mkdirs()
    withCloseable[FileOutputStream](() => new FileOutputStream(file),
      fos => properties.store(fos, "Default properties"))
  }

  def withCloseable[T <: Closeable](create: () => T, operate: T => Unit) {
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