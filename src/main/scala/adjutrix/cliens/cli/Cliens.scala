package adjutrix.cliens.cli

import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.adapter.AdapterFactory
import scopt.mutable.OptionParser

object Cliens {
  val config = Configuration.load
  var entity: String
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
      val adapter = AdapterFactory(config, entity)
      val cli = CLIFactory(config, entity)
      cli.optionList(adapter.findAll)
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
      val adapter = AdapterFactory(config, entity)
      val cli = CLIFactory(config, entity)
      cli.optionRow(adapter.findById(id))
    }
  }
}
