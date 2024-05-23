package com.AG_AP.electroshop.firebase

import android.annotation.SuppressLint
import android.util.Log
import com.AG_AP.electroshop.firebase.models.Activity
import com.AG_AP.electroshop.firebase.models.BusinessPartner
import com.AG_AP.electroshop.firebase.models.DocumentLineFireBase
import com.AG_AP.electroshop.firebase.models.OrderFireBase
import io.realm.kotlin.ext.query

object OrderCRUD : ActionFirebase {
    /*
    @SuppressLint("StaticFieldLeak")
    var database: FirebaseFirestore = DatabaseInitializer.database

    val coleccion = "SEIorders"
    override fun insert(data: Any) {
        val dataAux: OrderFireBase
        if (data is OrderFireBase) {
            dataAux = data
            database
                .collection(coleccion)
                .document(dataAux.DocNum.toString())
                .set(dataAux.toHashMap())
                .addOnSuccessListener {
                    Log.e("DataFireBase", "Creado Business partner: ${it.toString()}")
                }
                .addOnFailureListener { e ->
                    Log.w("DataFireBase", "Error añadiendo el documento $e")
                }
        }
    }

    fun insertForFireBase(data: Any) {
        val dataAux: OrderFireBase
        if (data is OrderFireBase) {
            dataAux = data
            database
                .collection(coleccion)
                .document()
                .set(dataAux.toHashMap())
                .addOnSuccessListener {
                    Log.e("DataFireBase", "Creado Business partner: ${it.toString()}")
                }
                .addOnFailureListener { e ->
                    Log.w("DataFireBase", "Error añadiendo el documento $e")
                }
        }
    }

    override fun getObjectById(id: Int, callback: (Any?) -> Unit) {
        ItemCRUD.database
            .collection(coleccion)
            .document(id.toString())
            .get()
            .addOnSuccessListener {
                if (it.exists()) {
                    val dato = it.data
                    val idFireBase = dato?.get("idFireBase").toString()
                    val DocNum = dato?.get("DocNum") as Long
                    val CardCode = dato.get("CardCode") as String
                    val CardName = dato.get("CardName") as String
                    val DocDate = dato.get("DocDate") as String
                    val DocDueDate = dato.get("DocDueDate") as String
                    val TaxDate = dato.get("TaxDate") as String
                    val DiscountPercent = dato.get("DiscountPercent") as Double
                    val SAP = dato.get("SAP").toString().toBoolean()
                    var SalesPersonCode =0
                    if(dato.get("SalesPersonCode") != null){
                        SalesPersonCode = dato.get("SalesPersonCode").toString().toInt() ?: 0
                    }

                    var documentLine: MutableList<DocumentLineFireBase> = mutableListOf()
                    try {
                        val documentLineAux = dato["DocumentLines"] as List<HashMap<String, Any>>
                        for (x in documentLineAux) {
                            val ItemCode = x["ItemCode"].toString()
                            val ItemDescription = x["ItemDescription"].toString()
                            val Quantity = x["Quantity"].toString().toDouble()
                            val DiscountPercentLine = x["DiscountPercent"].toString().toDouble()
                            val LineNum = x["LineNum"].toString().toInt()
                            val Price = x["Price"].toString().toDouble()

                            documentLine.add(
                                DocumentLineFireBase(
                                    ItemCode,
                                    ItemDescription,
                                    Quantity,
                                    DiscountPercentLine,
                                    LineNum,
                                    Price
                                )
                            )
                        }
                    } catch (e: Exception) {
                        println(e.message)
                    }

                    val Order: OrderFireBase = OrderFireBase(
                        idFireBase,
                        DocNum.toInt(),
                        CardCode,
                        CardName,
                        DocDate,
                        DocDueDate,
                        TaxDate,
                        DiscountPercent,
                        documentLine.toList(),
                        SAP,
                        SalesPersonCode
                    )

                    callback(Order)

                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en get item por id, posiblemente no exista $it")
            }


    }

    override fun getObjectByIdToString(id: String, callback: (Any?) -> Unit) {
        ItemCRUD.database
            .collection(coleccion)
            .document(id)
            .get()
            .addOnSuccessListener {
                if (it.exists()) {
                    val dato = it.data
                    val idFireBase = dato?.get("idFireBase").toString()
                    val DocNum = dato?.get("DocNum") as Long
                    val CardCode = dato.get("CardCode") as String
                    val CardName = dato.get("CardName") as String
                    val DocDate = dato.get("DocDate") as String
                    val DocDueDate = dato.get("DocDueDate") as String
                    val TaxDate = dato.get("TaxDate") as String
                    val DiscountPercent = dato.get("DiscountPercent") as Double
                    val SAP = dato.get("SAP").toString().toBoolean()
                    var SalesPersonCode =0
                    if(dato.get("SalesPersonCode") != null){
                        SalesPersonCode = dato.get("SalesPersonCode").toString().toInt() ?: 0
                    }

                    var documentLine: MutableList<DocumentLineFireBase> = mutableListOf()
                    try {
                        val documentLineAux = dato["DocumentLines"] as List<HashMap<String, Any>>
                        for (x in documentLineAux) {
                            val ItemCode = x["ItemCode"].toString()
                            val ItemDescription = x["ItemDescription"].toString()
                            val Quantity = x["Quantity"].toString().toDouble()
                            val DiscountPercentLine = x["DiscountPercent"].toString().toDouble()
                            val LineNum = x["LineNum"].toString().toInt()
                            val Price = x["Price"].toString().toDouble()

                            documentLine.add(
                                DocumentLineFireBase(
                                    ItemCode,
                                    ItemDescription,
                                    Quantity,
                                    DiscountPercentLine,
                                    LineNum,
                                    Price
                                )
                            )
                        }
                    } catch (e: Exception) {
                        println(e.message)
                    }

                    val Order: OrderFireBase = OrderFireBase(
                        idFireBase,
                        DocNum.toInt(),
                        CardCode,
                        CardName,
                        DocDate,
                        DocDueDate,
                        TaxDate,
                        DiscountPercent,
                        documentLine.toList(),
                        SAP,
                        SalesPersonCode
                    )

                    callback(Order)

                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en get item por id, posiblemente no exista $it")
            }
    }

    override fun getAllObject(callback: (MutableList<*>?) -> Unit) {
        database
            .collection(coleccion)
            .get()
            .addOnSuccessListener { lista ->
                val listObject = mutableListOf<OrderFireBase>()

                for (document in lista) {
                    val dato = document.data
                    val idFireBase = dato?.get("idFireBase").toString()
                    val DocNum = dato.get("DocNum") as Long
                    val CardCode = dato.get("CardCode") as String
                    val CardName = dato.get("CardName") as String
                    val DocDate = dato.get("DocDate") as String
                    val DocDueDate = dato.get("DocDueDate") as String
                    val TaxDate = dato.get("TaxDate") as String
                    val DiscountPercent = dato.get("DiscountPercent") as Double
                    val SAP = dato.get("SAP").toString().toBoolean()
                    var SalesPersonCode =0
                    if(dato.get("SalesPersonCode") != null){
                        SalesPersonCode = dato.get("SalesPersonCode").toString().toInt() ?: 0
                    }

                    var documentLine: MutableList<DocumentLineFireBase> = mutableListOf()
                    try {
                        val documentLineAux = dato["DocumentLines"] as List<HashMap<String, Any>>
                        for (x in documentLineAux) {
                            val ItemCode = x["ItemCode"].toString()
                            val ItemDescription = x["ItemDescription"].toString()
                            val Quantity = x["Quantity"].toString().toDouble()
                            val DiscountPercentLine =
                                x["DiscountPercent"].toString().toDouble() ?: 0.0
                            val LineNum = x["LineNum"].toString().toInt() ?: 0
                            val Price = x["Price"].toString().toDouble()

                            documentLine.add(
                                DocumentLineFireBase(
                                    ItemCode,
                                    ItemDescription,
                                    Quantity,
                                    DiscountPercentLine,
                                    LineNum,
                                    Price
                                )
                            )
                        }
                    } catch (e: Exception) {
                        println(e.message)
                    }

                    val Order: OrderFireBase = OrderFireBase(
                        idFireBase,
                        DocNum.toInt(),
                        CardCode,
                        CardName,
                        DocDate,
                        DocDueDate,
                        TaxDate,
                        DiscountPercent.toDouble(),
                        documentLine.toList(),
                        SAP,
                        SalesPersonCode
                    )


                    listObject.add(Order)
                }
                callback(listObject)
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en get all item $it")
                callback(mutableListOf(""))
            }
    }

    override fun updateObjectById(data: Any) {
        val dataAux: OrderFireBase
        if (data is OrderFireBase) {
            dataAux = data
            database
                .collection(coleccion)
                .document(dataAux.DocNum.toString())
                .update(dataAux.toHashMap())
                .addOnSuccessListener {
                    Log.e("Pruebas", "Updateado el order con id: ${dataAux.DocNum.toString()}")
                }
                .addOnFailureListener {
                    Log.e("Errores", "Erroren la order por id $dataAux.DocNum.toString()")
                }
        }
    }

    override suspend fun deleteObjectById(id: String) {
        database
            .collection(coleccion)
            .document(id)
            .delete()
            .addOnSuccessListener {
                Log.e("Pruebas", "Borrado el order con id: $id")
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en delete order por id $id")
            }
    }

 */


    val realm = DatabaseInitializer.realm

    override fun insert(data: Any) {
        val orderFireBase = data as OrderFireBase

        realm.writeBlocking {
            copyToRealm(orderFireBase)
        }
    }

    override fun getObjectById(id: Int, callback: (Any?) -> Unit) {
        val byId =
            realm.query<OrderFireBase>("idFireBase = $0", id.toString()).first().find() as OrderFireBase
        callback(byId)

    }

    override fun getObjectByIdToString(id: String, callback: (Any?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getAllObject(callback: (MutableList<*>?) -> Unit) {
        val all = realm.query<OrderFireBase>().find() as MutableList<*>?
        callback(all)
    }

    override suspend fun updateObjectById(data: Any) {
        val orderFireBase = data as OrderFireBase
        realm.query<OrderFireBase>("idFireBase = $0", orderFireBase.idFireBase)
            .first()
            .find()
            ?.also { oldActivity ->

                /*
                *  var idFireBase: String? = null,
                    var DocNum: Int,
                    var CardCode: String,
                    var CardName: String,
                    var DocDate: String,
                    var DocDueDate: String,
                    var TaxDate: String,
                    var DiscountPercent: Double,
                    var DocumentLines: List<DocumentLineFireBase>,
                    var SAP: Boolean,
                    var SalesPersonCode:Int,
                *
                * */
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
                        //
                    }
                }
            }
    }

    override suspend fun deleteObjectById(id: String) {
        val deleteObejct = realm.query<OrderFireBase>("idFireBase = $0", id)
        realm.writeBlocking {
            delete(deleteObejct)
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