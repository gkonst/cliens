package adjutrix.cliens.conf

trait TestConfiguration {
  Configuration.baseDir = "target"
  implicit val conf = Configuration.load
}

