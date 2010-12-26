package adjutrix.cliens.model

/**
 * 'Category' model.
 *
 * @author Konstantin_Grigoriev
 */
class Category(var name: String,
               var categoryType: CategoryType.Value,
               var defaultStorage: Option[Storage] = None,
               id: Option[Int] = None) extends Model(id)

object CategoryType extends Enumeration {
    val EXPENSE, INCOME = Value
    type CategoryType = Value

    implicit def intToCategoryType(id: Int) = {
        values.filter(p => p.id == id).head
    }
}
