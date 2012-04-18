package adjutrix.cliens.cli

import scopt.mutable.OptionParser
import adjutrix.cliens.service.Service

object Cliens {
  var entity: String = null
  var options: CLIOption = NoOption()

  val parser = new OptionParser("cliens") {
    arg("<entity>", "<entity> is name of entity to manipulate", {
      v: String => entity = v
    })
  }

  def main(args: Array[String]) {
    if (args.length == 0) {
      Console.err.println("\nError: missing argument: <action>\n\nUsage: cliens <action>\n")
      return
    }
    entity = null
    options = null
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
      Service(entity).list(options)
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
      Service(entity).view(id, options)
    }
  }
}
