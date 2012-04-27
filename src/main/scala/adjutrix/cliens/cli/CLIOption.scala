package adjutrix.cliens.cli

sealed trait CLIOption

case class NoOption() extends CLIOption

case class Verbose(anotherOption: CLIOption = NoOption()) extends CLIOption
