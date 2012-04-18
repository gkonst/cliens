package adjutrix.cliens.conf

import org.specs2.mutable.Specification


abstract class ConfigurationSpec extends Specification {

  def configurationShouldBeCorrect(configuration: Configuration) = {
    "not Null" in {
      configuration must not beNull
    }
    "username not empty" in {
      configuration.username must not beEmpty
    }
    "password not empty" in {
      configuration.password must not beEmpty
    }
    "url not empty" in {
      configuration.url must not beEmpty
    }
  }
}