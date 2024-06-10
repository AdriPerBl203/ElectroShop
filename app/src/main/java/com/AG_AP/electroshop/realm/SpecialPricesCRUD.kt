package com.AG_AP.electroshop.realm

import com.AG_AP.electroshop.realm.models.SEIConfig
import com.AG_AP.electroshop.realm.models.SpecialPriceRealm
import io.realm.kotlin.delete
import io.realm.kotlin.ext.query

object SpecialPricesCRUD : ActionRealm {

    val realm = DatabaseInitializer.realm

    override fun insert(data: Any) {
        val dataInsert = data as SpecialPriceRealm

        realm.writeBlocking {
            copyToRealm(dataInsert)
        }
    }
    //BASURA getObjectById
    @Deprecated("")
    override fun getObjectById(id: Int, callback: (Any?) -> Unit) {
        val byId =
            realm.query<SpecialPriceRealm>("idFireBase = $0", id.toString()).first().find() as SEIConfig
        callback(byId)

    }

    fun getSpecialPriceByCardCode(cardCode: String, callback: (Any?) -> Unit) {
        val byId =
            realm.query<SpecialPriceRealm>("CardCode = $0", cardCode).first().find() as SpecialPriceRealm
        callback(byId)
    }

    fun getSpecialPriceByItemCode(itemCode: String, callback: (Any?) -> Unit) {
        val byId =
            realm.query<SpecialPriceRealm>("ItemCode = $0", itemCode).first().find() as SpecialPriceRealm
        callback(byId)
    }

    fun getSpecialPrice(cardCode: String, itemCode: String, callback: (SpecialPriceRealm?) -> Unit) {
        try {
            val result = realm.query<SpecialPriceRealm>("CardCode = $0 AND ItemCode = $1", cardCode, itemCode)
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
        val all = realm.query<SpecialPriceRealm>().find() as MutableList<*>?
        callback(all)
    }

    //ESTA NO ES NECESARIO EN LOS PRECIOS ESPECIALES
    override suspend fun updateObjectById(data: Any) {}


    override suspend fun deleteObjectById(id: String) {
        //TODO
        val deleteObejct = realm.query<SpecialPriceRealm>("Code = $0", id)
        realm.writeBlocking {
            delete(deleteObejct)
        }
    }

    fun deleteAll() {
        realm.writeBlocking {
            delete<SpecialPriceRealm>()
        }
    }
}