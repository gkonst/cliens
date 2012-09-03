package adjutrix.cliens.model

sealed abstract class Model extends Product {
  val id: Option[Int]
  val resourceURI: Option[String]
}

case class Related[T <: Model](resourceURI: String, value: Option[T] = None) {
  def get = value match {
    case Some(x) => x
    case None => throw new IllegalStateException("Related fields is not filled.")
  }
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
                   storageType: Related[StorageType],
                   currencyType: Related[CurrencyType],
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
