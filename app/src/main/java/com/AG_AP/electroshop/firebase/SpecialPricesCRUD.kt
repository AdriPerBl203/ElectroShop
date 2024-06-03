package com.AG_AP.electroshop.firebase

import com.AG_AP.electroshop.firebase.models.SEIConfig
import com.AG_AP.electroshop.firebase.models.SpecialPriceFireBase
import io.realm.kotlin.delete
import io.realm.kotlin.ext.query

object SpecialPricesCRUD : ActionFirebase {

    val realm = DatabaseInitializer.realm

    override fun insert(data: Any) {
        val dataInsert = data as SpecialPriceFireBase

        realm.writeBlocking {
            copyToRealm(dataInsert)
        }
    }
    //BASURA getObjectById
    @Deprecated("")
    override fun getObjectById(id: Int, callback: (Any?) -> Unit) {
        val byId =
            realm.query<SpecialPriceFireBase>("idFireBase = $0", id.toString()).first().find() as SEIConfig
        callback(byId)

    }

    fun getSpecialPriceByCardCode(cardCode: String, callback: (Any?) -> Unit) {
        val byId =
            realm.query<SpecialPriceFireBase>("CardCode = $0", cardCode).first().find() as SpecialPriceFireBase
        callback(byId)
    }

    fun getSpecialPriceByItemCode(itemCode: String, callback: (Any?) -> Unit) {
        val byId =
            realm.query<SpecialPriceFireBase>("ItemCode = $0", itemCode).first().find() as SpecialPriceFireBase
        callback(byId)
    }

    fun getSpecialPrice(cardCode: String, itemCode: String, callback: (SpecialPriceFireBase?) -> Unit) {
        try {
            val result = realm.query<SpecialPriceFireBase>("CardCode = $0 AND ItemCode = $1", cardCode, itemCode)
                .first()
                .find()
            callback(result)
        } catch (e: Exception) {
            e.printStackTrace()
            callback(null)
        }
    }

    override fun getObjectByIdToString(id: String, callback: (Any?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getAllObject(callback: (MutableList<*>?) -> Unit) {
        val all = realm.query<SpecialPriceFireBase>().find() as MutableList<*>?
        callback(all)
    }

    //ESTA NO ES NECESARIO EN LOS PRECIOS ESPECIALES
    override suspend fun updateObjectById(data: Any) {}


    override suspend fun deleteObjectById(id: String) {
        //TODO
        val deleteObejct = realm.query<SpecialPriceFireBase>("Code = $0", id)
        realm.writeBlocking {
            delete(deleteObejct)
        }
    }

    fun deleteAll() {
        realm.writeBlocking {
            delete<SpecialPriceFireBase>()
        }
    }
}