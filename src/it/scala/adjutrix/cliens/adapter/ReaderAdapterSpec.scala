package adjutrix.cliens.adapter

import adjutrix.cliens.model._

abstract class ReaderAdapterSpec[M <: Model] extends AdapterSpec[M, ReaderAdapterComponent[M]] {
  self: ReaderAdapterComponent[M] =>

  val unknownId = 0

  def findAllIsDefined() {
    "findAll" should "return Right(Some(non empty items)" in {
      val result = adapter.findAll()
      result must be('right)
      result.right.get must be('defined)
      result.right.get.get must not be ('empty)
      result.right.get.get.foreach(_ must not be (null))
    }
  }

  def findByIdIsDefined(fixtureId: Int) {
    "findById" should "return Right(Some(item with id equesl to " + fixtureId + ")" in {
      val result = adapter.findById(fixtureId)
      result must be('right)
      result.right.get must be('defined)
      result.right.get.get.id must be(Some(fixtureId))
      specifyFields(result.right.get.get)
    }

    it should "return None if nothing found" in {
      val result = adapter.findById(unknownId)
      result must be('right)
      result.right.get must be(None)
    }
  }

  def specifyFields(result: M)
}
