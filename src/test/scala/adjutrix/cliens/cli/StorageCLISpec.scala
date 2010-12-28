package adjutrix.cliens.cli

import CLISpec.CLISpec
import adjutrix.cliens.model.{StorageSpec, Storage}
import collection.immutable.List

/**
 * Specification for {@link StorageCLI}.
 *
 * @author Konstantin_Grigoriev
 */

object StorageCLISpec extends CLISpec[Storage]("storage") {
    def givenModel = {
        StorageSpec.storage(Some(5))
    }

    def expectedRowSummaryValues = List("5", "Parex", "10.0", "$", "Bill")

    def expectedHeaderValues = List("Id", "Name", "Amount", "Type")
}