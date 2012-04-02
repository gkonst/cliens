package adjutrix.cliens.cli

import org.github.scopt.OptionParser
import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.adapter.AdapterFactory

object Cliens {
  val config = Configuration.load

  val parser = new OptionParser("cliens") {
    arg("<entity>", "<entity> is name of entity to manipulate", {
      v: String => config.entity = v
    })
  }

  def main(args: Array[String]) {
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
    parser.booleanOpt("s", "show", "show full information or not", {
      v: Boolean => config.showFull = v
    })
    if (parser.parse(args)) {
      val adapter = AdapterFactory(config, config.entity)
      val cli = CLIFactory(config, config.entity)
      cli.optionList(adapter.findAll)
    }
  }

  def view(args: Array[String]) {
    parser.booleanOpt("s", "show", "show full information or not", {
      v: Boolean => config.showFull = v
    })
    var id = 0
    parser.arg("<id>", "<id> item identifier", {
      v: String => id = Integer.valueOf(v).intValue
    })
    if (parser.parse(args)) {
      val adapter = AdapterFactory(config, config.entity)
      val cli = CLIFactory(config, config.entity)
      cli.optionRow(adapter.findById(id))
    }
  }
}
