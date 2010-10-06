package adjutrix.cliens.cli

import org.github.scopt.OptionParser
import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.adapter.AdapterFactory

/**
 * Main CLI class for Cliens.
 *
 * @author konstantin_grigoriev
 */
object Cliens {
    val config = new Configuration("http://127.0.0.1:8000/api", "kostya", "s")

    val parser = new OptionParser("cliens") {
        arg("<entity>", "<entity> is name of entity to manipulate", {v: String => config.entity = v})
    }

    def main(args: Array[String]) {
        val action = args.head
        action match {
            case "list" => list(args.tail)
            case "view" => view(args.tail)
        }
    }

    def list(args: Array[String]) {
        parser.booleanOpt("s", "show", "show full information or not", {v: Boolean => config.showFull = v})
        if (parser.parse(args)) {
            val adapter = AdapterFactory(config, config.entity)
            val printer = CLIFactory(config, config.entity)
            printer.list(adapter.findAll)
        }
    }

    def view(args: Array[String]) {
        parser.booleanOpt("s", "show", "show full information or not", {v: Boolean => config.showFull = v})
        var id = 0
        parser.arg("<id>", "<id> item identifier", {v: String => id = Integer.valueOf(v).intValue})
        if (parser.parse(args)) {
            val adapter = AdapterFactory(config, config.entity)
            val printer = CLIFactory(config, config.entity)
            printer.row(adapter.findById(id))
        }
    }
}