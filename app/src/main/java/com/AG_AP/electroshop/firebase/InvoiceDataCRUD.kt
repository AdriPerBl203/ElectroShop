package com.AG_AP.electroshop.firebase

import com.AG_AP.electroshop.firebase.models.InvoiceData
import io.realm.kotlin.delete
import io.realm.kotlin.ext.query

object InvoiceDataCRUD {

    val realm = DatabaseInitializer.realm

    fun insert(data: InvoiceData) {
        val dataInsert = data

        realm.writeBlocking {
            copyToRealm(dataInsert)
        }
    }

    fun getObjectByIdToString(id: String, callback: (InvoiceData?) -> Unit) {
        val ObjectbyId =
            realm.query<InvoiceData>("CardCode = $0", id).find().first()
        callback(ObjectbyId)
    }

    fun getAllObject(callback: (MutableList<InvoiceData>?) -> Unit) {
        val ObjectbyId =
            realm.query<InvoiceData>().find()
        callback(ObjectbyId.toMutableList())
    }

    suspend fun updateObjectById(data: InvoiceData) {
        realm.query<InvoiceData>("CardCode == $0", data.CardCode)
            .find()
            .first()
            .also { oldObject ->
                realm.write {
                    findLatest(oldObject)?.let { it ->
                        it.CardCode = data.CardCode
                        it.Invoice = data.Invoice
                    }
                }
            }
    }

    fun deleteAll() {
        realm.writeBlocking {
            delete<InvoiceData>()
        }

    }

}