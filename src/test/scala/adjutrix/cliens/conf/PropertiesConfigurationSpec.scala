package adjutrix.cliens.conf

import java.io.File

class PropertiesConfigurationSpec extends ConfigurationSpec {
  val configurationFile = new File("target/test-configuration.properties")

  "PropertiesConfiguration.loadFromDefault" should {
    "return correct configuration" in {
      configurationShouldBeCorrect(PropertiesConfiguration.loadFromDefault())
    }
  }

  "PropertiesConfiguration.loadFromDefaultAndSave" should {
    configurationFile.delete()
    "return correct configuration" in {
      configurationShouldBeCorrect(PropertiesConfiguration.loadFromDefaultAndSave(configurationFile))
    }
    "file should be created" in {
      configurationFile must exist
    }
  }
}
