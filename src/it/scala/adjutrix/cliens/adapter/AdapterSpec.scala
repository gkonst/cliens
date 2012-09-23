package adjutrix.cliens.adapter

import adjutrix.cliens.model._
import adjutrix.cliens.model.ModelsFactory._
import adjutrix.cliens.conf.DefaultConfiguration
import adjutrix.cliens.model.serializer.json.JSONSerializers
import org.scalatest.FlatSpec
import org.scalatest.matchers.MustMatchers

abstract class AdapterSpec[M <: Model] extends FlatSpec with MustMatchers with DefaultConfiguration with JSONSerializers {
  val unknownId = 0
  val adapter: Adapter[M]

  def findAllIsDefined() {
    "findAll" should "return Some with non empty items" in {
      val result = adapter.findAll()
      result must be('defined)
      result.get must not be ('empty)
      result.get.foreach(_ must not be (null))
    }
  }

  def findByIdIsDefined(fixtureId: Int) {
    "findById" should "return Some with id equesl to " + fixtureId in {
      val result = adapter.findById(fixtureId)
      result must be('defined)
      result.get.id must be(Some(fixtureId))
      specifyFields(result.get)
    }

    it should "return None if nothing found" in {
      adapter.findById(unknownId) must be(None)
    }
  }

  def createIsDefined(given: => M) {
    "create" should "return Right with uri" in {
      val result = adapter.create(given)
      result must be('right)
      try {
        val created = adapter.findById(toId(result.right.get))
        created must be('defined)
        specifyFields(created.get)
      } finally {
        adapter.delete(toId(result.right.get))
      }
    }
    // TODO add tests for validation
  }

  def deleteIsDefined(given: => M) {
    "delete" should "not fail" in {
      val result = adapter.create(given)
      result must be('right)
      adapter.delete(toId(result.right.get))
      adapter.findById(toId(result.right.get)) must be(None)
    }
  }

  def specifyFields(result: M)
}
