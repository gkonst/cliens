package adjutrix.cliens.model.serializer

import java.lang.reflect.{ParameterizedType, Field}

package object json {
  def toAnyRef(x: Any): AnyRef = x match {
    case x: Byte => Byte.box(x)
    case x: Short => Short.box(x)
    case x: Int => Int.box(x)
    case x: Long => Long.box(x)
    case x: Float => Float.box(x)
    case x: Double => Double.box(x)
    case x: Char => Char.box(x)
    case x: Boolean => Boolean.box(x)
    case x: Unit => Unit.box(x)
    case x: AnyRef => x
    case null => null
  }

  def getFields[A](klass: Class[A]) = for (f <- klass.getDeclaredFields if !f.getName.contains("$")) yield f

  def getFieldType(field: Field) = {
    if (isOption(field)) {
      field.getGenericType.asInstanceOf[ParameterizedType].getActualTypeArguments.head match {
        case x: Class[_] => x
        case x: ParameterizedType => x.getRawType.asInstanceOf[Class[_]]
      }
    } else {
      field.getType
    }
  }

  def isOption(field: Field) = field.getType == classOf[Option[_]]
}