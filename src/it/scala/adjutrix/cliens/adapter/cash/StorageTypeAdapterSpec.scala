package adjutrix.cliens.adapter.cash

import adjutrix.cliens.model.StorageType
import adjutrix.cliens.adapter.ReaderAdapterSpec

class StorageTypeAdapterSpec extends ReaderAdapterSpec[StorageType] with StorageTypeAdapterComponent {

  it should behave like findAllIsDefined()
  it should behave like findByIdIsDefined(fixtureId = 1)

  def specifyFields(result: StorageType) {
    result.name must not be null
  }

}