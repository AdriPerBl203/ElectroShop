package com.AG_AP.electroshop.firebase

import com.AG_AP.electroshop.firebase.models.OrderFireBase
import com.AG_AP.electroshop.firebase.models.Price
import io.realm.kotlin.ext.query

object PriceListCRUD {

    val realm = DatabaseInitializer.realm

    fun insertPrecio(Price: Price) {
        realm.writeBlocking {
            copyToRealm(Price)
        }
    }

    fun getPrecioById(idPrecio: String, callback: (Price?) -> Unit) {
        val byId =
            OrderCRUD.realm.query<Price>("priceList = $0", idPrecio).first()
                .find() as Price
        callback(byId)
    }

    fun getAllPrecios(callback: (MutableList<Price>) -> Unit) {
        val all = realm.query<Price>().find() as MutableList<Price>
        callback(all)
    }

    suspend fun updatePrecioById(idPrecio: String, price: Price) {
        realm.query<Price>("priceList = $0", idPrecio)
            .first()
            .find()
            ?.also { oldActivity ->
                OrderCRUD.realm.write {
                    findLatest(oldActivity)?.let { it ->
                        it.priceList = price.priceList
                        it.price = price.price
                        it.currency = price.currency
                        it.SAP = it.SAP
                    }
                }
            }
    }

    fun deletePrecioById(idPrecio: String) {
        val deleteObejct = OrderCRUD.realm.query<OrderFireBase>("priceList = $0", idPrecio)
        OrderCRUD.realm.writeBlocking {
            delete(deleteObejct)
        }
    }

}