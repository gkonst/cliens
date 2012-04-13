package adjutrix.cliens.conf

trait PropertiesConfigurable extends Configurable {
  override def configuration = Configuration.load
}