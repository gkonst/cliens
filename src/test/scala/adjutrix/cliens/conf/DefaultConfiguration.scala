package adjutrix.cliens.conf

trait DefaultConfiguration {
  implicit val configuration = PropertiesConfiguration.loadFromDefault()
}
