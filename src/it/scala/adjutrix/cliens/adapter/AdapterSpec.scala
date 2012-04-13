package adjutrix.cliens.adapter

import org.specs2.mutable.Specification
import adjutrix.cliens.model.Model
import org.specs2.specification.Example
import adjutrix.cliens.conf.Configuration

abstract class AdapterSpec[M <: Model, T <: Adapter[M]](adapterName: String) extends Specification {
  Configuration.baseDir = "target"
  val fixtureId = 1
  val unknownId = 0
  val adapter = AdapterFactory(adapterName).asInstanceOf[T]
  val fullAdapterName = adapterName.capitalize + "Adapter"

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

  // TODO re-implement
  //  fullAdapterName + ".create" should {
  //    val given = createModel
  //    val result = adapter.create(given)
  //    try {
  //      "result be Some[Model]" in {
  //        result must beSome[Model]
  //      }
  //      "result must have id" in {
  //        result.get.id must beSome[Int]
  //      }
  //      specifyFields(result.get)
  //    } finally {
  //      adapter.delete(result.get.id.get)
  //    }
  //  }

  def createModel: M

  def specifyFields(result: M): Example
}
