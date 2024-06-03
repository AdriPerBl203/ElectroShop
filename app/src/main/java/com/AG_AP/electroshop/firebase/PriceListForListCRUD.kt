package com.AG_AP.electroshop.firebase

import com.AG_AP.electroshop.firebase.models.ItemPrice
import com.AG_AP.electroshop.firebase.models.PriceListRealm
import io.realm.kotlin.delete
import io.realm.kotlin.ext.query

object PriceListForListCRUD {

    val realm = DatabaseInitializer.realm

    fun insert(ItemPrice: PriceListRealm) {
        realm.writeBlocking {
            copyToRealm(ItemPrice)
        }
    }

    fun getPrecioById(idLista: Int, callback: (ItemPrice?) -> Unit) {
        val byId =
            realm.query<PriceListRealm>("priceList = $0", idLista).first()
                .find() as ItemPrice
        callback(byId)
    }

    fun getAllPrecios(callback: (MutableList<PriceListRealm>) -> Unit) {
        val all = realm.query<PriceListRealm>().find()
        callback(all.toMutableList())
    }

    @Deprecated("NO ES  NECESARIO DESARROLLAR ESTE MÉTODO")
    suspend fun updatePrecioById(idPrecio: String, itemPrice: ItemPrice) {
        realm.query<ItemPrice>("priceList == $0", idPrecio)
            .first()
            .find()
            ?.also { oldActivity ->
                realm.write {
                    findLatest(oldActivity)?.let { it ->
                        it.priceList = itemPrice.priceList
                        it.price = itemPrice.price
                        it.currency = itemPrice.currency
                        it.SAP = it.SAP
                    }
                }
            }
    }

    fun deleteAll() {
        SEIConfigCRUD.realm.writeBlocking {
            delete<PriceListRealm>()
        }
    }



}