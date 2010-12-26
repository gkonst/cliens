package adjutrix.cliens.adapter

import org.specs.Specification
import adjutrix.cliens.model.Model
import adjutrix.cliens.conf.TestConfiguration


object AdapterSpec extends Specification with TestConfiguration {

    //    "createData should return valid string" in {
    //        AdapterFactory(conf, adapters(0)).createData(Map("key" -> "value")) must beEqualTo("key=value")
    //        AdapterFactory(conf, adapters(0)).createData(Map("key" -> "value", "key1" -> "value1")) must beEqualTo("key=value&key1=value1")
    //    }

    /**
     * Specification for {@link Adapter} class and its subclasses.
     *
     * @author konstantin_grigoriev
     */
    abstract class AdapterSpecification[M <: Model, T <: Adapter[M]](adapterName: String) extends Specification with TestConfiguration {

        val fixtureId = 10
        val unknownId = 0

        val adapter = AdapterFactory(conf, adapterName).asInstanceOf[T]

        "Adapter.findAll" should {
            val result = adapter.findAll
            "return Some with List" in {
                result must beSome[List[_]]
            }
            "return List with not Null values" in {
                result.get.foreach(model => model must notBeNull)
            }
        }

        def createModel: M

        def checkFields(result: M)

        "Adapter.findById" should {
            val result = adapter.findById(fixtureId)
            "result be Some[Model]" in {
                result must beSome[Model]
            }
            "result is Model with id equal to " + fixtureId in {
                result.get.id must (beSome[Int] and beEqualTo(Some(fixtureId)))
            }
            checkFields(result.get)
            "result is None if nothing found" in {
                adapter.findById(unknownId) must beNone
            }
        }

        "Adapter.create" should {
            val given = createModel
            val result = adapter.create(given)
            try {
                "result be Some[Model]" in {
                    result must beSome[Model]
                }
                "result must have id" in {
                    result.get.id must beSome[Int]
                }
                checkFields(result.get)
            } finally {
                adapter.delete(result.get.id.get)
            }
        }
    }

}
