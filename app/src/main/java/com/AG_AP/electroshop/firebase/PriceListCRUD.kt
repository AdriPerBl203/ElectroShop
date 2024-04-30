package com.AG_AP.electroshop.firebase

import android.annotation.SuppressLint
import android.util.Log
import com.AG_AP.electroshop.firebase.models.Price
import com.google.firebase.firestore.FirebaseFirestore

object PriceListCRUD {

    @SuppressLint("StaticFieldLeak")
    var database: FirebaseFirestore = DatabaseInitializer.database

    val coleccion = "SEIlistaPrecios"

    fun insertPrecio(priceList: Int, price: Number, currency: String) {
        val precio = Price(priceList, price, currency).toHashMap()

        database
            .collection(this.coleccion)
            .document(priceList.toString())
            .set(precio)
            .addOnSuccessListener {
                Log.e("Pruebas", "Creado lista de precios: ${it.toString()}")
            }
            .addOnFailureListener { e ->
                Log.w("Errores", "Error añadiendo el documento $e")
            }
    }

    fun getPrecioById(idPrecio: String, callback: (Price?) -> Unit) {
        database
            .collection(this.coleccion)
            .document(idPrecio)
            .get()
            .addOnSuccessListener {
                if (it.exists()) {
                    val datosPrice = it.data
                    Log.e("Pruebas", "Datos: ${datosPrice.toString()}")


                    val priceListDatos = datosPrice?.get("priceList")
                    val priceDatos = datosPrice?.get("price")
                    val currency = datosPrice?.get("currency")

                    Log.wtf("Pruebas", "PriceList: ${priceListDatos.toString().toInt()}, PriceDatos: ${priceDatos.toString().toBigDecimal()}, currency: ${currency.toString()}")

                    val price = Price(priceListDatos.toString().toInt(), priceDatos.toString().toBigDecimal(), currency.toString())
                    callback(price)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en get precio por id, posiblemente no exista $it")
            }
    }

    fun getAllPrecios(callback: (MutableList<Price>) -> Unit) {
        database
            .collection(this.coleccion)
            .get()
            .addOnSuccessListener {
                lista ->
                val preciosList = mutableListOf<Price>()

                for (document in lista.documents) {
                    val datosPrice = document.data

                    val priceListDatos = datosPrice?.get("priceList")
                    val priceDatos = datosPrice?.get("price")
                    val currency = datosPrice?.get("currency")

                    val price = Price(
                        priceListDatos.toString().toInt(),
                        priceDatos.toString().toBigDecimal(),
                        currency.toString()
                    )
                    preciosList.add(price)
                }

                callback(preciosList)
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en get precios, posiblemente vacio $it")
                callback(mutableListOf())
            }
    }

    fun updatePrecioById(idPrecio: String, price: Price) {
        database
            .collection(this.coleccion)
            .document(idPrecio)
            .update(price.toHashMap())
            .addOnSuccessListener {
                Log.e("Pruebas", "Updateado el precio con id: $idPrecio")
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en get update precio por id $it")
            }
    }

    fun deletePrecioById(idPrecio: String) {
        database
            .collection(this.coleccion)
            .document(idPrecio)
            .delete()
            .addOnSuccessListener {
                Log.e("Pruebas", "Borrado el precio con id: $idPrecio")
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en delete precio por id $it")
            }
    }

}