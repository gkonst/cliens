package adjutrix.cliens.adapter

import adjutrix.cliens.model._
import adjutrix.cliens.model.ModelsFactory._
import adjutrix.cliens.conf.DefaultConfiguration
import adjutrix.cliens.model.serializer.json.JSONSerializers
import org.scalatest.FlatSpec
import org.scalatest.matchers.MustMatchers

abstract class AdapterSpec[M <: Model] extends FlatSpec with MustMatchers with DefaultConfiguration with JSONSerializers {
  val fixtureId = 1
  val unknownId = 0
  val adapter: Adapter[M]

  "findAll" should "return Some with non empty items" in {
    val result = adapter.findAll()
    result must be('defined)
    result.get must not be ('empty)
    result.get.foreach(_ must not be (null))
  }

  "findById" should "return Some with id equesl to " + fixtureId in {
    val result = adapter.findById(fixtureId)
    result must be('defined)
    result.get.id must be(Some(fixtureId))
    specifyFields(result.get)
  }

  it should "return None if nothing found" in {
    adapter.findById(unknownId) must be(None)
  }

  "create" should "return Right with uri" in {
    val given = createModel
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

  def createModel: M

  def specifyFields(result: M)
}
