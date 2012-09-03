package adjutrix.cliens.model

object ModelsFactory {
  val storageType = StorageType("Bill", Some(1), resourceURI("storagetype", 1))
  val currencyType = CurrencyType("Dollar", "$", 1.0, Some(1), resourceURI("currencytype", 1))

  def storage(id: Option[Int] = Some(1)) = Storage("Parex", storageType, currencyType, 10.0, id, resourceURI("storage", id))

  def storageUnfilled(id: Option[Int] = Some(1)) =
    Storage("Parex", storageType.resourceURI, currencyType.resourceURI, 10.0, id, resourceURI("storage", id))

  def category(id: Option[Int] = None, defaultStorage: Option[Storage] = None) = new Category("Food", CategoryType.INCOME,
    defaultStorage, id)

  def resourceURI(resourceName: String, id: Int): Option[String] = Some("/api/v1/%s/%s/".format(resourceName, id))

  def resourceURI(resourceName: String, id: Option[Int]): Option[String] = id match {
    case Some(x) => resourceURI(resourceName, x)
    case None => None
  }
}