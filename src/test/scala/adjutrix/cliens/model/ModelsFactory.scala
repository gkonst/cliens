package adjutrix.cliens.model

import adjutrix.cliens.model.CategoryType._

object ModelsFactory {
  val storageType = StorageType("Bill", Some(1), toResourceURI("storagetype", 1))
  val currencyType = CurrencyType("Dollar", "$", 1.0, Some(1), toResourceURI("currencytype", 1))

  def storage(id: Option[Int] = Some(1)) = Storage("Parex", storageType, currencyType, 10.0, id, toResourceURI("storage", id))

  def storageUnfilled(id: Option[Int] = Some(1)) =
    Storage("Parex", storageType.resourceURI, currencyType.resourceURI, 10.0, id, toResourceURI("storage", id))

  def category(id: Option[Int] = None, categoryType: CategoryType = EXPENSE, defaultStorage: Option[Storage] = None) =
    Category("Food", categoryType, defaultStorage, id, toResourceURI("category", id))

  def categoryUnfilled(id: Option[Int] = None, categoryType: CategoryType = EXPENSE, defaultStorage: Option[Storage] = None) =
    Category("Food", categoryType, defaultStorage.map {
      _.resourceURI
    }, id, toResourceURI("category", id))

  def toResourceURI(resourceName: String, id: Int): Option[String] = Some("/api/v1/%s/%s/".format(resourceName, id))

  def toResourceURI(resourceName: String, id: Option[Int]): Option[String] = id match {
    case Some(x) => toResourceURI(resourceName, x)
    case None => None
  }

  def toId(resourceURI: String) = resourceURI.split('/').last.toInt
}