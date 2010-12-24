package adjutrix.cliens.adapter

import org.specs.Specification
import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.model.Model

trait TestConfiguration {
    val conf = new Configuration("http://127.0.0.1:8000/api", "test_user", "s")
}

object AdapterSpecification extends Specification with TestConfiguration {

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
            "result not Null" in {
                result must notBeNull
            }
            "result is Model with id equal to " + fixtureId in {
                result.asInstanceOf[Model].id must (notBeNull and beEqualTo(fixtureId))
            }
            checkFields(result)
        }

        "Adapter.create" should {
            var result = createModel
            result = adapter.create(result)
            try {
                "result not null" in {
                    result must notBeNull
                }
                "result must have id" in {
                    result.asInstanceOf[Model].id must notBeNull
                }
                checkFields(result)
            } finally {
                adapter.delete(result.id)
            }
        }
    }

}
