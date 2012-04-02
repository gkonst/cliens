package adjutrix.cliens.model

abstract class Model(val id: Option[Int] = None)

class CurrencyType(var name: String,
                   var abbr: String,
                   var rate: BigDecimal,
                   id: Option[Int] = None) extends Model(id)

class StorageType(val name: String, id: Option[Int] = None) extends Model(id)

class Storage(var name: String,
              val storageType: StorageType,
              val currencyType: CurrencyType,
              var amount: BigDecimal = 0,
              id: Option[Int] = None) extends Model(id)

object CategoryType extends Enumeration {
  val EXPENSE, INCOME = Value
  type CategoryType = Value

  implicit def intToCategoryType(id: Int) = {
    values.filter(p => p.id == id).head
  }
}

class Category(var name: String,
               var categoryType: CategoryType.Value,
               var defaultStorage: Option[Storage] = None,
               id: Option[Int] = None) extends Model(id)

class Expense(id: Option[Int] = None) extends Model(id)
