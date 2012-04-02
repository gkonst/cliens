package adjutrix.cliens.conf

trait TestConfiguration {
  Configuration.baseDir = "target"
  val conf = Configuration.load
}

