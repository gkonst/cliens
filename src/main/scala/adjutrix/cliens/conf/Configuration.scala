package adjutrix.cliens.conf

import java.util.Properties
import java.io.{InputStream, FileOutputStream, FileInputStream, File}
import adjutrix.cliens.util.{Logging, StreamUtil}

/**
 * Configuration model class, used to store and transfer configuration related values.
 *
 * @author konstantin_grigoriev
 */
case class Configuration(url: String,
                         username: String,
                         password: String,
                         var showFull: Boolean = false,
                         var entity: String = null)

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
    StreamUtil.withCloseable[FileInputStream](() => new FileInputStream(file),
      fis => properties.load(fis))
    Configuration(properties.getProperty("url"), properties.getProperty("username"),
      properties.getProperty("password"))
  }

  def createDefault() {
    val properties = new Properties
    StreamUtil.withCloseable[InputStream](() => classOf[Configuration]
      .getResourceAsStream("/default.configuration.properties"),
      fis => properties.load(fis))
    file.getParentFile.mkdirs
    StreamUtil.withCloseable[FileOutputStream](() => new FileOutputStream(file),
      fos => properties.store(fos, "Default properties"))
  }
}