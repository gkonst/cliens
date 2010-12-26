package adjutrix.cliens.model.serializer

import adjutrix.cliens.model.{Storage, Category}

/**
 * Serializer for {@link Category}.
 *
 * @author Konstantin_Grigoriev
 */

object CategorySerializer extends Serializer[Category] {

    def serialize(entity: Category) = serializeDefaultStorage(entity) ++ Map("name" -> entity.name, "type" -> entity.categoryType.id)

    def serializeDefaultStorage(entity: Category) = {
        entity.defaultStorage match {
            case Some(x) => Map("default_storage" -> x.id.get)
            case None => Map.empty[String, Any]
        }
    }

    def deserialize(data: Map[String, Any]) = {
        val id = getId(data)
        val name = data.get("name").get.asInstanceOf[String]
        val categoryType = data.get("type").get.asInstanceOf[Double].toInt
        val defaultStorage = data.get("default_storage") match {
            case Some(null) => None
            case Some(x) => Some(Serializer(classOf[Storage]).deserialize(x.asInstanceOf[Map[String, Any]]))
            case None => None
        }
        new Category(name, categoryType, defaultStorage, id)
    }
}