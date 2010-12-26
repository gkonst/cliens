package adjutrix.cliens.model

/**
 * 'CurrencyType' model.
 *
 * @author Konstantin_Grigoriev
 */
class CurrencyType(var name: String,
                   var abbr: String,
                   var rate: BigDecimal,
                   id: Option[Int] = None) extends Model(id)