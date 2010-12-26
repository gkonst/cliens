package adjutrix.cliens.model.serializer

import adjutrix.cliens.model.StorageType

/**
 * Serializer for {@link StorageType}.
 *
 * @author Konstantin_Grigoriev
 */

object StorageTypeSerializer extends Serializer[StorageType] {
    def deserialize(data: Map[String, Any]) = {
        val id = getId(data)
        val name = data.get("name").get.asInstanceOf[String]
        new StorageType(name, id)
    }

    def serialize(entity: StorageType) = Map("id" -> entity.id, "name" -> entity.name)
}