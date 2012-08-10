package adjutrix.cliens.model

sealed abstract class Model {
  val id: Option[Int]
  val resourceURI: Option[String]
}

case class CurrencyType(name: String,
                        abbr: String,
                        rate: BigDecimal,
                        id: Option[Int] = None,
                        resourceURI: Option[String] = None) extends Model

case class StorageType(name: String,
                       id: Option[Int] = None,
                       resourceURI: Option[String] = None) extends Model

case class Storage(name: String,
                   storageType: StorageType,
                   currencyType: CurrencyType,
                   amount: BigDecimal = 0,
                   id: Option[Int] = None,
                   resourceURI: Option[String] = None) extends Model

object CategoryType extends Enumeration {
  val EXPENSE, INCOME = Value
  type CategoryType = Value

  implicit def intToCategoryType(id: Int) = {
    values.filter(_.id == id).head
  }
}

case class Category(name: String,
                    categoryType: CategoryType.Value,
                    defaultStorage: Option[Storage] = None,
                    id: Option[Int] = None,
                    resourceURI: Option[String] = None) extends Model

case class Expense(id: Option[Int] = None,
                   resourceURI: Option[String] = None) extends Model
