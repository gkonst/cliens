package adjutrix.cliens.adapter

import adjutrix.cliens.model._

abstract class WriterAdapterSpec[M <: Model] extends AdapterSpec[M, WriterAdapterComponent[M]] {
  self: WriterAdapterComponent[M] =>

  def createIsDefined(given: => M) {
    "create" should "return Right with uri" in {
      val result = adapter.create(given)
      result must be('right)
      try {
        val created = adapter.findById(uriToId(result.right.get))
        created must be('right)
        created.right.get must be('defined)
        specifyFields(created.right.get.get)
      } finally {
        adapter.delete(uriToId(result.right.get))
      }
    }
  }

  def deleteIsDefined(given: => M) {
    "delete" should "not fail" in {
      val result = adapter.create(given)
      result must be('right)
      adapter.delete(uriToId(result.right.get))
      val found = adapter.findById(uriToId(result.right.get))
      found must be('right)
      found.right.get must be(None)
    }
  }

  def specifyFields(result: M)
}
