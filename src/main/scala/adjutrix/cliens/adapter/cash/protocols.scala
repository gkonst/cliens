package adjutrix.cliens.adapter.cash

import adjutrix.cliens.adapter.protocol.JSONProtocol
import adjutrix.cliens.model.{CurrencyType, Storage, Category}
import adjutrix.cliens.model.serializer.json.{CurrencyTypeSerializer, StorageSerializer, CategorySerializer}

trait CategoryProtocol extends JSONProtocol[Category] {
  def serializer = CategorySerializer
}

trait StorageProtocol extends JSONProtocol[Storage] {
  def serializer = StorageSerializer
}

trait CurrencyTypeProtocol extends JSONProtocol[CurrencyType] {
  def serializer = CurrencyTypeSerializer
}