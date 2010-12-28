package adjutrix.cliens.cli

import adjutrix.cliens.cli.CLISpec.CLISpec
import adjutrix.cliens.model.{CategorySpec, Category}

/**
 * Specification for {@link CategoryCLI}.
 *
 * @author Konstantin_Grigoriev
 */

object CategoryCLISpec extends CLISpec[Category]("category") {
    def givenModel = {
        CategorySpec.category(Some(4))
    }

    def expectedRowSummaryValues = List("4", "Food", "INCOME")

    def expectedHeaderValues = List("Id", "Name", "Type", "Default Storage")
}