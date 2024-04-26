package com.AG_AP.electroshop.firebase

import android.annotation.SuppressLint
import android.util.Log
import com.AG_AP.electroshop.firebase.models.Item
import com.AG_AP.electroshop.firebase.models.ItemType
import com.AG_AP.electroshop.firebase.models.Price
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.firestore.persistentCacheSettings

object ItemCRUD {

    @SuppressLint("StaticFieldLeak")
    private var database: FirebaseFirestore = getInstance()

    val coleccion = "SEIproductos"

    fun getInstance(): FirebaseFirestore {
        database = FirebaseFirestore.getInstance()

        val settings = firestoreSettings {
            setLocalCacheSettings(memoryCacheSettings { setupCacheSize() })
            setLocalCacheSettings(persistentCacheSettings {})
        }

        database.firestoreSettings = settings

        return database
    }

    private fun setupCacheSize() {
        val settings = firestoreSettings {
            setLocalCacheSettings(persistentCacheSettings {
                // Set size to 100 MB
                setSizeBytes(1024 * 1024 * 100)
            })
        }
        database.firestoreSettings = settings
    }


    fun insertItem(item: Item) {
        database
            .collection(coleccion)
            .document(item.itemName)
            .set(item.toHashMap())
            .addOnSuccessListener {
                Log.e("Pruebas", "Se ha insertado el item ${item.toString()}")
            }
            .addOnFailureListener {
                Log.e("Error", "Ha ocurrido un error añadiendo el item: $item, error: $it")
            }
    }

    /*
    fun getItemById(itemId: String, callback: (Item?) -> Unit) {
        var item: Item?

        database
            .collection(coleccion)
            .document(itemId)
            .get()
            .addOnSuccessListener {
                if (it.exists()) {
                    val datosItem = it.data

                    val itemName = datosItem?.get("ItemName") as String
                    val itemTypeString = datosItem["ItemType"] as String
                    val mainSupplier = datosItem["Mainsupplier"] as String
                    val documentoPrecio = datosItem["ItemPrices"] as ArrayList<*>
                    var itemPrice = mutableListOf<Price>()

                    if (documentoPrecio.isNotEmpty()) {
                        for (documentoVer in documentoPrecio) {
                            val referenciaDocumento = documentoVer as? DocumentReference
                            val idDocumento = referenciaDocumento?.id

                            PriceListCRUD.getPrecioById(idDocumento.toString()) { price ->
                                if (price != null) {
                                    itemPrice.add(price)
                                }
                            }
                        }
                    }

                    val itemType: ItemType = when (itemTypeString) {
                        "I" -> ItemType.I
                        "L" -> ItemType.L
                        "T" -> ItemType.T
                        "F" -> ItemType.F
                        else -> ItemType.I
                    }

                    item = Item(
                        itemName,
                        itemType,
                        mainSupplier,
                        itemPrice
                    )

                    callback(item)

                } else {

                    callback(null)
                }
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en get item por id, posiblemente no exista $it")
            }
    }

     */

    fun getItemById(itemId: String, callback: (Item?) -> Unit) {
        database
            .collection(coleccion)
            .document(itemId)
            .get()
            .addOnSuccessListener {
                if (it.exists()) {
                    val datosItem = it.data


                    val itemName = datosItem?.get("ItemName") as String
                    val itemTypeString = datosItem["ItemType"] as String
                    val mainSupplier = datosItem["Mainsupplier"] as String
                    var itemPrice: MutableList<Price>? = mutableListOf()
                    try {
                        //Busca por "ItemPrices", si salta una excepcion es que no existe
                        val listaPrecios = datosItem["ItemPrices"] as List<HashMap<String, Any>>

                        if (listaPrecios.isNotEmpty()) {
                            for (precio in listaPrecios) {
                                val currency = precio["Currency"].toString()
                                val price = precio["Price"].toString().toInt()
                                val priceList = precio["PriceList"].toString().toInt()

                                itemPrice?.add(Price(priceList, price, currency))
                            }
                        } else {
                            itemPrice = null
                        }
                    } catch (e: Exception) {
                        itemPrice = null
                    }


                    val itemType: ItemType = when (itemTypeString) {
                        "I" -> ItemType.I
                        "L" -> ItemType.L
                        "T" -> ItemType.T
                        "F" -> ItemType.F
                        else -> ItemType.I
                    }

                    val item = Item(
                        itemName,
                        itemType,
                        mainSupplier,
                        itemPrice
                    )

                    callback(item)

                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en get item por id, posiblemente no exista $it")
            }


    }

    fun updateItemById(itemId: String, item: Item) {
        database
            .collection(coleccion)
            .document(itemId)
            .update(item.toHashMap())
            .addOnSuccessListener {
                Log.e("Pruebas", "Updateado el item con id: $itemId")
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en get update item por id $it")
            }
    }

    fun deleteItemById(itemId: String) {
        database
            .collection(coleccion)
            .document(itemId)
            .delete()
            .addOnSuccessListener {
                Log.e("Pruebas", "Borrado el item con id: $itemId")
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en delete item por id $it")
            }
    }
}
