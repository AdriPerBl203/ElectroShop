package com.AG_AP.electroshop.firebase

import android.annotation.SuppressLint
import android.util.Log
import com.AG_AP.electroshop.firebase.models.Item
import com.AG_AP.electroshop.firebase.models.ItemType
import com.AG_AP.electroshop.firebase.models.Price
import com.AG_AP.electroshop.firebase.models.SpecialPriceFireBase
import com.google.firebase.firestore.FirebaseFirestore

object SpecialPricesCRUD {

    @SuppressLint("StaticFieldLeak")
    var database: FirebaseFirestore = DatabaseInitializer.database

    val coleccion = "SEIpreciosEspecial"

    fun insertSpecialPrice(specialPrice: SpecialPriceFireBase) {
        this.database
            .collection(this.coleccion)
            .document("${specialPrice.ItemCode}${specialPrice.CardCode}")
            .set(specialPrice.toHashMap())
            .addOnSuccessListener {
                Log.e("Pruebas", "Se ha insertado el precio especial ${specialPrice.toString()}")
            }
            .addOnFailureListener {
                Log.e("Error", "Ha ocurrido un error añadiendo el specialPrice: $specialPrice, error: $it")
            }
    }
    //TODO
    /*fun getItemById(itemId: String, callback: (Item?) -> Unit) {
        database
            .collection(coleccion)
            .document(itemId)
            .get()
            .addOnSuccessListener {
                if (it.exists()) {
                    val datosItem = it.data

                    val idFireBase = datosItem?.get("idFireBase").toString()
                    val itemName = datosItem?.get("ItemName") as String
                    val itemCode = datosItem["ItemCode"] as String ?: ""
                    val itemTypeString = datosItem["ItemType"] as String
                    val mainSupplier = datosItem["Mainsupplier"] as String?
                    var itemPrice: MutableList<Price>? = mutableListOf()
                    try {
                        //Busca por "ItemPrices", si salta una excepcion es que no existe
                        val listaPrecios = datosItem["ItemPrices"] as List<HashMap<String, Any>>

                        if (listaPrecios.isNotEmpty()) {
                            for (precio in listaPrecios) {
                                val currency = precio["Currency"].toString()
                                val price = precio["Price"].toString().toDouble()
                                val priceList = precio["PriceList"].toString().toInt()
                                val SAP = precio["SAP"].toString().toBoolean()

                                itemPrice?.add(Price(priceList, price, currency, SAP))
                            }
                        } else {
                            itemPrice = null
                        }
                    } catch (e: Exception) {
                        Log.e("Errores", e.stackTraceToString())
                        itemPrice = null
                    }

                    val manageSerialNumbers = datosItem["ManageSerialNumbers"] as String
                    val autoCreateSerialNumbersOnRelease = datosItem["AutoCreateSerialNumbersOnRelease"] as String
                    val SAP = datosItem["SAP"].toString().toBoolean()

                    val itemType: ItemType = when (itemTypeString) {
                        "I" -> ItemType.Articulo
                        "L" -> ItemType.Servicio
                        "T" -> ItemType.Viaje
                        "F" -> ItemType.ActivoFijo
                        else -> ItemType.Articulo
                    }

                    val item = Item(
                        idFireBase,
                        itemCode,
                        itemName,
                        itemType,
                        mainSupplier,
                        itemPrice,
                        manageSerialNumbers,
                        autoCreateSerialNumbersOnRelease,
                        SAP
                    )

                    callback(item)

                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en get item por id, posiblemente no exista $it")
            }


    }*/

    //TODO
    /*fun getAllItems(callback: (MutableList<Item>) -> Unit) {
        database
            .collection(coleccion)
            .get()
            .addOnSuccessListener { lista ->
                val listaItems = mutableListOf<Item>()

                for (document in lista) {
                    val datosItem = document.data

                    val idFireBase = datosItem?.get("idFireBase").toString()
                    val itemName = datosItem.get("ItemName") as String
                    val itemCode = datosItem.get("ItemCode") as String
                    val itemTypeString = datosItem["ItemType"] as String
                    val mainSupplier = datosItem["Mainsupplier"] as String?
                    var itemPrice: MutableList<Price>? = mutableListOf()
                    try {
                        //Busca por "ItemPrices", si salta una excepcion es que no existe
                        val listaPrecios = datosItem["ItemPrices"] as List<HashMap<String, Any>>

                        if (listaPrecios.isNotEmpty()) {
                            for (precio in listaPrecios) {
                                val currency = precio["Currency"].toString()
                                val price = precio["Price"].toString().toInt()
                                val priceList = precio["PriceList"].toString().toInt()
                                val SAP = precio["SAP"].toString().toBoolean()

                                itemPrice?.add(Price(priceList, price, currency, SAP))
                            }
                        } else {
                            itemPrice = null
                        }
                    } catch (e: Exception) {
                        itemPrice = null
                    }

                    val manageSerialNumbers = datosItem["ManageSerialNumbers"] as String
                    val autoCreateSerialNumbersOnRelease = datosItem["AutoCreateSerialNumbersOnRelease"] as String
                    val SAP = datosItem["SAP"].toString().toBoolean()

                    val itemType: ItemType = when (itemTypeString) {
                        "I" -> ItemType.Articulo
                        "L" -> ItemType.Servicio
                        "T" -> ItemType.Viaje
                        "F" -> ItemType.ActivoFijo
                        else -> ItemType.Articulo
                    }

                    val item = Item(
                        idFireBase,
                        itemCode,
                        itemName,
                        itemType,
                        mainSupplier,
                        itemPrice,
                        manageSerialNumbers,
                        autoCreateSerialNumbersOnRelease,
                        SAP
                    )

                    listaItems.add(item)
                }
                callback(listaItems)
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en get all item $it")
                callback(mutableListOf())
            }
    }*/

    fun deleteSpecialPriceById(id: String) {
        this.database
            .collection(this.coleccion)
            .document(id)
            .delete()
            .addOnSuccessListener {
                Log.e("Pruebas", "Borrado el SpecialPrice con id: $id")
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en SpecialPrice item por id $it")
            }
    }
}