package adjutrix.cliens.adapter

import org.specs.Specification
import adjutrix.cliens.conf.Configuration

trait TestConfiguration {
    val conf = new Configuration("http://127.0.0.1:8000/api", "kostya", "s")
}

/**
 * Specification for    { @link Adapter } class and its subclasses.
 *
 * @author konstantin_grigoriev
 */
object AdapterSpecification extends Specification with TestConfiguration {
    val adapters = Array("storage", "category", "expense")

    "createData should return valid string" in {
        AdapterFactory(conf, adapters(0)).createData(Map("key" -> "value")) must beEqualTo("key=value")
        AdapterFactory(conf, adapters(0)).createData(Map("key" -> "value", "key1" -> "value1")) must beEqualTo("key=value&key1=value1")
    }

    "findAll should return Some of Entities" in {
        adapters.foreach(adapter => AdapterFactory(conf, adapter).findAll must beSome[Any])
    }

    "findById should return Map of Any" in {
        adapters.foreach(adapter => AdapterFactory(conf, adapter).findById(10))
    }
}

object CategoryAdapterSpecification extends Specification with TestConfiguration {
    val adapter = AdapterFactory(conf, "category").asInstanceOf[CategoryAdapter]

    "CategoryAdapter.create" should {
        val item = adapter.create(Map("name" -> "TestCategory", "type" -> 0))
        "return not null" in {
            item must notBeNull
        }
        "return Map[Any, Any]" in {
            item must haveSuperClass[Map[Any, Any]]
        }
        "return must have id" in {
            item must haveKey("id")
        }
        adapter.delete(item.get("id").get.asInstanceOf[Double].toInt)
    }

    "CategoryAdapter.findExpenseCategories should return Some of Entities" in {
        adapter.findExpenseCategories must beSomething
    }

    "CategoryAdapter.findExpenseCategories should return entities with type=0" in {
        adapter.findExpenseCategories.get.asInstanceOf[List[Map[Any, Any]]].
                foreach(entity => entity.get("type").get must beEqualTo(0))
    }

}

object StorageAdapterSpecification extends Specification with TestConfiguration {
    val adapter = AdapterFactory(conf, "storage")

    "StorageAdapter.create" should {
        val item = adapter.create(Map("name" -> "TestStorage", "amount" -> 0, "currency_type" -> 1, "type" -> 3))
        "return not null" in {
            item must notBeNull
        }
        "return Map[Any, Any]" in {
            item must haveSuperClass[Map[Any, Any]]
        }
        "return must have id" in {
            item must haveKey("id")
        }
        adapter.delete(item.get("id").get.asInstanceOf[Double].toInt)
    }

    "StorageAdapter.create should throw ValidationException" in {
        adapter.create(Map("name" -> "TestStorage", "amount" -> 0)) must throwA[ValidationException]
    }
}