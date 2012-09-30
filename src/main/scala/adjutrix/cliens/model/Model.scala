package adjutrix.cliens.model

sealed abstract class Model extends Product {
  val id: Option[Int]
  val resourceURI: Option[String]

  if (id.isDefined) {
    require(resourceURI.isDefined, "If id is defined, resourceURI should also be defined")
  }
}

case class Related[T <: Model](resourceURI: String, value: Option[T] = None) {
  def get: T = value match {
    case Some(x) => x
    case None => throw new IllegalStateException("Related fields is not filled.")
  }
}

case class Pack[T <: Model](next: String, previous: String, objects: List[T]) extends Seq[T] {
  def length = objects.length

  def iterator = objects.iterator

  def apply(idx: Int) = objects(idx)
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

import CategoryType._

case class Category(name: String,
                    categoryType: CategoryType,
                    defaultStorage: Option[Related[Storage]] = None,
                    id: Option[Int] = None,
                    resourceURI: Option[String] = None) extends Model

case class Expense(id: Option[Int] = None,
                   resourceURI: Option[String] = None) extends Model
