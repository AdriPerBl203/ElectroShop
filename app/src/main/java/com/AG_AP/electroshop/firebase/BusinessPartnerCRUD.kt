package com.AG_AP.electroshop.firebase

import android.annotation.SuppressLint
import android.util.Log
import com.AG_AP.electroshop.firebase.models.Activity
import com.AG_AP.electroshop.firebase.models.BusinessPartner
import com.google.firebase.firestore.FirebaseFirestore

object BusinessPartnerCRUD : ActionFirebase {

    @SuppressLint("StaticFieldLeak")
    var database: FirebaseFirestore = DatabaseInitializer.database

    val coleccion = "SEIbusinessPartner"
    override fun insert(data: Any) {
        val dataAux: BusinessPartner
        if(data is BusinessPartner){
            dataAux = data
            this.database
                .collection(this.coleccion)
                .document(dataAux.CardCode.toString())
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
        this.database
            .collection(this.coleccion)
            .document(id.toString())
            .get()
            .addOnSuccessListener {
                if (it.exists()) {
                    val data = it.data
                    Log.e("Pruebas", "Datos: ${data.toString()}")


                    val CardCode = data?.get("CardCode").toString() ?: ""
                    val CardType = data?.get("CardType").toString() ?: ""
                    val CardName = data?.get("CardName").toString() ?: ""
                    val Cellular = data?.get("Cellular").toString() ?: ""
                    val EmailAddress = data?.get("EmailAddress").toString() ?: ""

                    val dataReturn: BusinessPartner = BusinessPartner(
                        CardCode,
                        CardType,
                        CardName,
                        Cellular,
                        EmailAddress
                    )
                    callback(dataReturn)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en get business partner por id, posiblemente no exista $it")
            }
    }

    override fun getObjectByIdToString(id: String, callback: (Any?) -> Unit) {
        this.database
            .collection(this.coleccion)
            .document(id)
            .get()
            .addOnSuccessListener {
                if (it.exists()) {
                    val data = it.data
                    Log.e("Pruebas", "Datos: ${data.toString()}")


                    val CardCode = data?.get("CardCode").toString() ?: ""
                    val CardType = data?.get("CardType").toString() ?: ""
                    val CardName = data?.get("CardName").toString() ?: ""
                    val Cellular = data?.get("Cellular").toString() ?: ""
                    val EmailAddress = data?.get("EmailAddress").toString() ?: ""

                    val dataReturn: BusinessPartner = BusinessPartner(
                        CardCode,
                        CardType,
                        CardName,
                        Cellular,
                        EmailAddress
                    )
                    callback(dataReturn)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en get business partner por id, posiblemente no exista $it")
            }
    }

    override fun getAllObject(callback: (MutableList<*>?) -> Unit) {
        this.database
            .collection(this.coleccion)
            .get()
            .addOnSuccessListener {
                    lista ->
                val dataList = mutableListOf<BusinessPartner>()

                for (document in lista.documents) {
                    val data = document.data

                    val CardCode = data?.get("CardCode").toString()
                    val CardType = data?.get("CardType").toString()
                    val CardName = data?.get("CardName").toString()
                    val Cellular = data?.get("Cellular").toString()
                    val EmailAddress = data?.get("EmailAddress").toString()

                    val dataReturn: BusinessPartner = BusinessPartner(
                        CardCode,
                        CardType,
                        CardName,
                        Cellular,
                        EmailAddress
                    )

                    dataList.add(dataReturn)
                }

                callback(dataList)
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en get BusinessPartner, posiblemente vacio $it")
                callback(null)
            }
    }

    override fun updateObjectById(data: Any) {
        val dataAux: BusinessPartner
        if(data is BusinessPartner){
            dataAux = data
            this.database
                .collection(this.coleccion)
                .document(dataAux.CardCode.toString())
                .update(dataAux.toHashMap())
                .addOnSuccessListener {
                    Log.e("Pruebas", "Updateado el cliente con id: ${dataAux.CardCode}")
                }
                .addOnFailureListener {
                    Log.e("Errores", "Error  la actividad por id $it")
                }
        }
    }

    override suspend fun deleteObjectById(id: String) {
        this.database
            .collection(this.coleccion)
            .document(id)
            .delete()
            .addOnSuccessListener {
                Log.e("Pruebas", "Borrado del cliente con id: $id")
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en delete cliente por id $it")
            }
    }
}