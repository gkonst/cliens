package adjutrix.cliens.conf

trait Configuration {
  def url: String

  def username: String

  def password: String
}

trait Configurable {
  def configuration: Configuration
}