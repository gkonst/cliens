package adjutrix.cliens.cli

import scopt.mutable.OptionParser
import adjutrix.cliens.cli.cash.CashCLI

class Cliens(cliFactory: CLIFactory = CashCLI) {
  var entity: String = null
  implicit var options: CLIOption = NoOption()

  val parser = new OptionParser("cliens") {
    arg("<entity>", "<entity> is name of entity to manipulate", {
      v: String => entity = v
    })
  }

  def run(args: Array[String]) {
    if (args.length == 0) {
      Console.err.println("\nError: missing argument: <action>\n\nUsage: cliens <action>\n")
      return
    }
    val action = args.head
    action match {
      case "list" => list(args.tail)
      case "view" => view(args.tail)
    }
  }

  def list(args: Array[String]) {
    parser.booleanOpt("v", "verbose", "show full information or not", {
      v: Boolean => options = Verbose(options)
    })
    if (parser.parse(args)) {
      cliFactory(entity) match {
        case cli: ListCLI[_] => cli.list
      }
    }
  }

  def view(args: Array[String]) {
    parser.booleanOpt("v", "verbose", "show full information or not", {
      v: Boolean => options = Verbose(options)
    })
    var id = 0
    parser.arg("<id>", "<id> item identifier", {
      v: String => id = Integer.valueOf(v).intValue
    })
    if (parser.parse(args)) {
      cliFactory(entity) match {
        case cli: DetailCLI[_] => cli.detail(id)
      }
    }
  }
}

object Cliens extends App {
  new Cliens().run(args)
}
