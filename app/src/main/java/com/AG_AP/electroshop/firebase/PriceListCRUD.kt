package com.AG_AP.electroshop.firebase

import com.AG_AP.electroshop.firebase.models.ItemPrice
import com.AG_AP.electroshop.firebase.models.PriceListRealm
import com.AG_AP.electroshop.firebase.models.SEIConfig
import io.realm.kotlin.delete
import io.realm.kotlin.ext.query

object PriceListCRUD {

    val realm = DatabaseInitializer.realm

    fun insertPrecio(ItemPrice: ItemPrice) {
        realm.writeBlocking {
            copyToRealm(ItemPrice)
        }
    }

    fun getPrecioById(idPrecio: String, callback: (ItemPrice?) -> Unit) {
        val byId =
            realm.query<ItemPrice>("priceList = $0", idPrecio).first()
                .find() as ItemPrice
        callback(byId)
    }

    fun getAllPrecios(callback: (MutableList<ItemPrice>) -> Unit) {
        val all = realm.query<ItemPrice>().find()
        callback(all.toMutableList())
    }

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

    suspend fun deletePrecioById(idPrecio: String) {

        val deleteObejct = realm.query<ItemPrice>("priceList == $0", idPrecio).find()
        realm.writeBlocking {
            if(!deleteObejct.isNotEmpty()){
                delete(deleteObejct)
            }
        }
    }

}