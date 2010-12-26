package adjutrix.cliens.model

/**
 * 'Storage' model.
 *
 * @author Konstantin_Grigoriev
 */
class Storage(var name: String,
              val storageType: StorageType,
              val currencyType: CurrencyType,
              var amount: BigDecimal = 0,
              id: Option[Int] = None) extends Model(id)
