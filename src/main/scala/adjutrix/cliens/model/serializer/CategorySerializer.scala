package adjutrix.cliens.model.serializer

import adjutrix.cliens.model.Category

/**
 * Serializer for {@link Category}.
 *
 * @author Konstantin_Grigoriev
 */

object CategorySerializer extends Serializer[Category] {
    def serialize(entity: Category) = Map("name" -> entity.name, "type" -> entity.categoryType.id, "default_storage_id" -> entity.defaultStorageId)

    def deserialize(data: Map[String, Any]) = {
        val id = data.get("id").get.asInstanceOf[Double].toInt
        val name = data.get("name").get.asInstanceOf[String]
        val categoryType = data.get("type").get.asInstanceOf[Double].toInt
        new Category(name, categoryType, id = id)
    }
}