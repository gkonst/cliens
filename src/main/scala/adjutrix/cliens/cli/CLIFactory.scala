package adjutrix.cliens.cli

trait CLIFactory {
  def apply(name: String): CLI[_]
}