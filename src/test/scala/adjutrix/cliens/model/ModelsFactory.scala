package adjutrix.cliens.model

object ModelsFactory {
  val storageType = new StorageType("Bill", Some(1))
  val currencyType = new CurrencyType("Dollar", "$", 1.0, Some(1))

  def storage(id: Option[Int] = Some(1)) = new Storage("Parex", storageType, currencyType, 10.0, id)

  def category(id: Option[Int] = None, defaultStorage: Option[Storage] = None) = new Category("Food", CategoryType.INCOME,
    defaultStorage, id)
}