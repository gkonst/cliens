package adjutrix.cliens.adapter

import org.specs2.mutable.Specification
import adjutrix.cliens.model._
import adjutrix.cliens.model.ModelsFactory._
import org.specs2.specification.Example
import adjutrix.cliens.conf.DefaultConfiguration
import adjutrix.cliens.model.serializer.json.JSONSerializers

abstract class AdapterSpec[M <: Model] extends Specification with DefaultConfiguration with JSONSerializers {
  val fixtureId = 1
  val unknownId = 0
  val adapter: Adapter[M]
  lazy val fullAdapterName = adapter.getClass.getSimpleName

  fullAdapterName + ".findAll" should {
    val result = adapter.findAll()
    "return Some with List" in {
      result must beSome[Seq[_]]
    }
    "return List with not Null values" in {
      result.get must have(_ must not beNull)
    }
  }

  fullAdapterName + ".findById" should {
    val result = adapter.findById(fixtureId)
    "result be Some[Model]" in {
      result must beSome[Model]
    }
    "result is Model with id equal to " + fixtureId in {
      result.get.id must (beSome[Int] and beEqualTo(Some(fixtureId)))
    }
    specifyFields(result.get)
    "result is None if nothing found" in {
      adapter.findById(unknownId) must beNone
    }
  }

  fullAdapterName + ".create should return correct result" in {
    val given = createModel
    val result = adapter.create(given)
    "result be Some[Model]" in {
      result must beRight[String]
    }
    val id = toId(result.right.get)
    try {
      val created = adapter.findById(id)
      "created be Some" in {
        created must beSome[Model]
      }
      specifyFields(created.get)
    } finally {
      adapter.delete(id)
    }
  }

  def createModel: M

  def specifyFields(result: M): Example
}
