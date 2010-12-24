package adjutrix.cliens.model

/**
 * 'Storage' model.
 *
 * @author Konstantin_Grigoriev
 */

class Storage(var name: String,
              val storageType: Int,
              val currencyType: Int,
              var amount: BigDecimal = 0,
              id: Int = null.asInstanceOf[Int]) extends Model(id)