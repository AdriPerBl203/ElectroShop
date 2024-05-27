package com.AG_AP.electroshop.firebase

import com.AG_AP.electroshop.firebase.models.BusinessPartner
import com.AG_AP.electroshop.firebase.models.OrderFireBase
import io.realm.kotlin.delete
import io.realm.kotlin.ext.query

object OrderCRUD : ActionFirebase {


    val realm = DatabaseInitializer.realm

    override fun insert(data: Any) {
        val orderFireBase = data as OrderFireBase

        realm.writeBlocking {
            copyToRealm(orderFireBase)
        }
    }

    fun insertForFireBase(data: Any) {
        TODO()
    }


    override fun getObjectById(id: Int, callback: (Any?) -> Unit) {
        val byId =
            realm.query<OrderFireBase>("DocNum = $0", id.toString()).first()
                .find() as OrderFireBase
        callback(byId)
    }

    override fun getObjectByIdToString(id: String, callback: (Any?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getAllObject(callback: (MutableList<*>?) -> Unit) {
        val all = realm.query<OrderFireBase>().find()
        callback(all.toMutableList())
    }

    override suspend fun updateObjectById(data: Any) {
        val orderFireBase = data as OrderFireBase
        realm.query<OrderFireBase>("DocNum = $0", orderFireBase.DocNum)
            .first()
            .find()
            ?.also { oldActivity ->
                realm.write {
                    findLatest(oldActivity)?.let { it ->
                        it.DocNum = orderFireBase.DocNum
                        it.CardCode = orderFireBase.CardCode
                        it.CardName = orderFireBase.CardName
                        it.DocDate = orderFireBase.DocDate
                        it.DocDueDate = orderFireBase.DocDueDate
                        it.TaxDate = orderFireBase.TaxDate
                        it.DiscountPercent = orderFireBase.DiscountPercent
                        it.DocumentLines = orderFireBase.DocumentLines
                        it.SAP = orderFireBase.SAP
                        it.SalesPersonCode = orderFireBase.SalesPersonCode
                        it.Slpcode = orderFireBase.Slpcode
                        //
                    }
                }
            }
    }

    override suspend fun deleteObjectById(id: String) {
        val deleteObejct = realm.query<OrderFireBase>("DocNum = $0", id).find().firstOrNull()
        if (deleteObejct != null) {
            realm.writeBlocking {
                delete(deleteObejct)
            }
        }
    }

    fun deleteAll() {
        realm.writeBlocking {
            delete<OrderFireBase>()
        }
    }


    fun insertForFireBase(data: BusinessPartner) {
        //TODO("Not yet implemented")
        /*val docRef = this.database
            .collection(this.coleccion)
            .document()
        val newdata = data.copy(idFireBase = docRef.id) // Guardar el ID generado dentro de la actividad
        docRef
            .set(newdata.toHashMap())
            .addOnSuccessListener {
                Log.e("FireBase", "Creado Business Partner con ID: ${docRef.id}")
            }
            .addOnFailureListener { e ->
                Log.w("FireBase", "Error añadiendo el documento $e")
            }*/
    }
}