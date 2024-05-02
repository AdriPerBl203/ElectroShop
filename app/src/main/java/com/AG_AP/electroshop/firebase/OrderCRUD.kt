package com.AG_AP.electroshop.firebase

import android.annotation.SuppressLint
import android.util.Log
import com.AG_AP.electroshop.firebase.models.DocumentLineFireBase
import com.AG_AP.electroshop.firebase.models.OrderFireBase
import com.google.firebase.firestore.FirebaseFirestore

object OrderCRUD : ActionFirebase {
    @SuppressLint("StaticFieldLeak")
    var database: FirebaseFirestore = DatabaseInitializer.database

    val coleccion = "SEIorders"
    override fun insert(data: Any) {
        val dataAux: OrderFireBase
        if(data is OrderFireBase){
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

    override fun getObjectById(id: Int, callback: (Any?) -> Unit) {
        ItemCRUD.database
            .collection(coleccion)
            .document(id.toString())
            .get()
            .addOnSuccessListener {
                if (it.exists()) {
                    val dato = it.data

                    val DocNum = dato?.get("DocNum") as Long
                    val CardCode = dato.get("CardCode") as String
                    val CardName = dato.get("CardName") as String
                    val DocDate = dato.get("DocDate") as String
                    val DocDueDate = dato.get("DocDueDate") as String
                    val TaxDate = dato.get("TaxDate") as String
                    val DiscountPercent = dato.get("DiscountPercent") as Double

                    var documentLine: MutableList<DocumentLineFireBase> = mutableListOf()
                    try {
                        val documentLineAux = dato["DocumentLines"] as List<HashMap<String, Any>>
                        for (x in documentLineAux) {
                            val ItemCode = x["ItemCode"].toString()
                            val Quantity = x["Quantity"].toString().toDouble()
                            //val DiscountPercent = x["DiscountPercent"].toString().toDouble()
                            //val LineNum = x["LineNum"].toString().toInt()
                            val Price = x["Price"].toString().toDouble()

                            documentLine.add(
                                DocumentLineFireBase(
                                    ItemCode,
                                    Quantity,
                                    0.0,
                                    0,
                                    Price
                                )
                            )
                        }
                    } catch (e: Exception) {
                        println(e.message)
                    }

                    val Order: OrderFireBase = OrderFireBase(
                        DocNum.toInt(),
                        CardCode,
                        CardName,
                        DocDate,
                        DocDueDate,
                        TaxDate,
                        DiscountPercent,
                        documentLine.toList()
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

                    val DocNum = dato.get("DocNum") as Long
                    val CardCode = dato.get("CardCode") as String
                    val CardName = dato.get("CardName") as String
                    val DocDate = dato.get("DocDate") as String
                    val DocDueDate = dato.get("DocDueDate") as String
                    val TaxDate = dato.get("TaxDate") as String
                    val DiscountPercent = dato.get("DiscountPercent") as Double

                    var documentLine: MutableList<DocumentLineFireBase> = mutableListOf()
                    try {
                        val documentLineAux = dato["DocumentLines"] as List<HashMap<String, Any>>
                            for (x in documentLineAux) {
                                val ItemCode = x["ItemCode"].toString()
                                val Quantity = x["Quantity"].toString().toDouble()
                                //val DiscountPercent = x["DiscountPercent"].toString().toDouble() ?: 0.0
                                //val LineNum = x["LineNum"].toString().toInt() ?: 0
                                val Price = x["Price"].toString().toDouble()

                                documentLine.add(
                                    DocumentLineFireBase(
                                        ItemCode,
                                        Quantity,
                                        0.0,
                                        0,
                                        Price
                                    )
                                )
                            }
                    } catch (e: Exception) {
                        println(e.message)
                    }

                    val Order: OrderFireBase = OrderFireBase(
                        DocNum.toInt(),
                        CardCode,
                        CardName,
                        DocDate,
                        DocDueDate,
                        TaxDate,
                        DiscountPercent.toDouble(),
                        documentLine.toList()
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
        if(data is OrderFireBase){
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
}