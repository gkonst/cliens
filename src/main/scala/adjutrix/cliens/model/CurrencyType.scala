package adjutrix.cliens.model

/**
 * 'CurrencyType' model.
 *
 * @author Konstantin_Grigoriev
 */
class CurrencyType(var name: String,
                   var abbr: String,
                   var rate: BigDecimal,
                   id: Int = null.asInstanceOf[Int]) extends Model(id)