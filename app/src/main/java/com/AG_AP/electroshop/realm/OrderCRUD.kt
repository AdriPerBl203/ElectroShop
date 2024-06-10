package com.AG_AP.electroshop.realm

import com.AG_AP.electroshop.realm.models.BusinessPartner
import com.AG_AP.electroshop.realm.models.OrderRealm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.Sort

object OrderCRUD : ActionRealm {


    val realm = DatabaseInitializer.realm

    override fun insert(data: Any) {
        val orderRealm = data as OrderRealm

        realm.writeBlocking {
            copyToRealm(orderRealm)
        }
    }

    fun insertForFireBase(data: Any) {
        TODO()
    }


    override fun getObjectById(id: Int, callback: (Any?) -> Unit) {
        val byId =
            realm.query<OrderRealm>("DocNum = $0", id.toString()).first()
                .find() as OrderRealm
        callback(byId)
    }

    override fun getObjectByIdToString(id: String, callback: (Any?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getAllObject(callback: (MutableList<*>?) -> Unit) {
        val all = realm.query<OrderRealm>().find()
        callback(all.toMutableList())
    }

    /**
     * Method that receives a boolean, if its true then it returns the list of orders which are in sap
     * otherwise it returns those which aren't
     */
    fun getOrdersInSap(sap: Boolean, callback: (MutableList<*>?) -> Unit) {
        val all = realm.query<OrderRealm>("SAP == $0", sap).find()
        callback(all.toMutableList())
    }

    override suspend fun updateObjectById(data: Any) {
        val orderRealm = data as OrderRealm
        realm.query<OrderRealm>("DocNum = $0", orderRealm.DocNum)
            .find()
            .first()
            .also { oldActivity ->
                realm.write {
                    findLatest(oldActivity)?.let { it ->
                        it.DocNum = orderRealm.DocNum
                        it.CardCode = orderRealm.CardCode
                        it.CardName = orderRealm.CardName
                        it.DocDate = orderRealm.DocDate
                        it.DocDueDate = orderRealm.DocDueDate
                        it.TaxDate = orderRealm.TaxDate
                        it.DiscountPercent = orderRealm.DiscountPercent
                        it.DocumentLines = orderRealm.DocumentLines
                        it.SAP = orderRealm.SAP
                        it.SalesPersonCode = orderRealm.SalesPersonCode
                        it.Slpcode = orderRealm.Slpcode
                        //
                    }
                }
            }
    }

    suspend fun updateOrderToSAPTrue() {
        realm.query<OrderRealm>("SAP == $0", false).find().also { list ->
            realm.write {
                list.forEach { obj ->
                    findLatest(obj)?.apply {
                        SAP = true
                    }

                }
            }
        }

    }

    override suspend fun deleteObjectById(id: String) {
        val deleteObejct = realm.query<OrderRealm>("DocNum = $0", id).find().firstOrNull()
        if (deleteObejct != null) {
            realm.writeBlocking {
                val del = findLatest(deleteObejct)
                if (del != null) {
                    delete(del)
                }
            }
        }
    }

    fun deleteAll() {
        val deleteObject = realm.query<OrderRealm>("SAP == $0", true).find()
        if (deleteObject.isNotEmpty()) {
            realm.writeBlocking {
                deleteObject.forEach {
                    val objetoABorrar = findLatest(it)
                    if (objetoABorrar != null) {
                        delete(objetoABorrar)
                    }
                }
            }
        }

    }

    fun getAllObjectOrdeByCarCodeAndDocDueDate(cardCode: String,callback: (MutableList<OrderRealm>?) -> Unit) {
        val all = realm.query<OrderRealm>("CardCode = $0", cardCode).sort("CardName", Sort.ASCENDING)
            .sort("DocDueDate", Sort.ASCENDING)
            .find()
        callback(all.toMutableList())
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