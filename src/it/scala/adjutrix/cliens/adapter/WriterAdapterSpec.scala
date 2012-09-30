package adjutrix.cliens.adapter

import adjutrix.cliens.model._
import adjutrix.cliens.model.ModelsFactory._

abstract class WriterAdapterSpec[M <: Model] extends AdapterSpec[M, WriterAdapterComponent[M]] {
  self: WriterAdapterComponent[M] =>

  def createIsDefined(given: => M) {
    "create" should "return Right with uri" in {
      val result = adapter.create(given)
      result must be('right)
      try {
        val created = adapter.findById(toId(result.right.get))
        created must be('right)
        created.right.get must be('defined)
        specifyFields(created.right.get.get)
      } finally {
        adapter.delete(toId(result.right.get))
      }
    }
  }

  def deleteIsDefined(given: => M) {
    "delete" should "not fail" in {
      val result = adapter.create(given)
      result must be('right)
      adapter.delete(toId(result.right.get))
      val found = adapter.findById(toId(result.right.get))
      found must be('right)
      found.right.get must be(None)
    }
  }

  def specifyFields(result: M)
}
