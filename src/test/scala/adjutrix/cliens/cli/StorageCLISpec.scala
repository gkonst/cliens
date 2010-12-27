package adjutrix.cliens.cli

import CLISpec.CLISpec
import adjutrix.cliens.model.{StorageSpec, Storage}

/**
 * Specification for {@link StorageCLI}.
 *
 * @author Konstantin_Grigoriev
 */

object StorageCLISpec extends CLISpec[Storage]("storage") {
    def givenModel = {
        StorageSpec.storage(Some(5))
    }

    def expectedRowSummary = "id -> 5, name -> Parex, amount -> 10.0, currency -> Dollar, type -> Bill"
}