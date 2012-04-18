package adjutrix.cliens.conf

trait PropertiesConfigurable extends Configurable {
  implicit override def configuration = Configuration.load
}