package adjutrix.cliens.cli

import java.io.{BufferedReader, PrintStream}

trait SystemIO {
  def in: BufferedReader = Console.in

  def out: PrintStream = Console.out
}
