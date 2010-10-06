package adjutrix.cliens.scala

import org.specs.Specification
import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.adapter.{ValidationException, AdapterFactory}

trait TestConfiguration {
    val conf = new Configuration("http://127.0.0.1:8000/api", "kostya", "s")
}

/**
 * Specification for  { @link Adapter } class and its subclasses.
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
        adapters.foreach(adapter => AdapterFactory(conf, adapter).findById(10) must notBeNull)
    }
}

object CategoryAdapterSpecification extends Specification with TestConfiguration {
    "CategoryAdapter.create should return Map of Any" in {
        AdapterFactory(conf, "category").create(Map("name" -> "TestCategory", "type" -> 0)) must notBeNull
    }
}

object StorageAdapterSpecification extends Specification with TestConfiguration {
    "StorageAdapter.create should return Map of Any" in {
        AdapterFactory(conf, "storage").create(Map("name" -> "TestStorage", "amount" -> 0, "currency_type" -> 1, "type" -> 3)) must notBeNull
    }

    "StorageAdapter.create should throw ValidationException" in {
        AdapterFactory(conf, "storage").create(Map("name" -> "TestStorage", "amount" -> 0)) must throwA[ValidationException]
    }
}