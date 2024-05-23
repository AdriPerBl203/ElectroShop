package com.AG_AP.electroshop.firebase

import android.annotation.SuppressLint
import android.util.Log
import com.AG_AP.electroshop.firebase.models.Activity
import com.AG_AP.electroshop.firebase.models.BusinessPartner
import com.AG_AP.electroshop.firebase.models.Item
import com.AG_AP.electroshop.firebase.models.ItemType
import com.AG_AP.electroshop.firebase.models.Price
import io.realm.kotlin.ext.query

object ItemCRUD : ActionFirebase {
    /*
    @SuppressLint("StaticFieldLeak")
    var database: FirebaseFirestore = DatabaseInitializer.database

    val coleccion = "SEIproductos"

    fun insertItem(item: Item) {
        database
            .collection(coleccion)
            .document(item.ItemCode)
            .set(item.toHashMap())
            .addOnSuccessListener {
                Log.e("Pruebas", "Se ha insertado el item ${item.toString()}")
            }
            .addOnFailureListener {
                Log.e("Error", "Ha ocurrido un error añadiendo el item: $item, error: $it")
            }
    }

    fun insertItemForFireBase(item: Item) {
        val docRef = this.database
            .collection(this.coleccion)
            .document()
        val newdata = item.copy(idFireBase = docRef.id) // Guardar el ID generado dentro de la actividad
        docRef
            .set(newdata.toHashMap())
            .addOnSuccessListener {
                Log.e("FireBase", "Creado item con ID: ${docRef.id}")
            }
            .addOnFailureListener { e ->
                Log.w("FireBase", "Error añadiendo el documento $e")
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
            .collection(this.coleccion)
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


    }

    fun getAllItems(callback: (MutableList<Item>) -> Unit) {
        database
            .collection(this.coleccion)
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
    }


    fun updateItemById(item: Item) {
        database
            .collection(this.coleccion)
            .document(item.ItemCode)
            .update(item.toHashMap())
            .addOnSuccessListener {
                Log.e("Pruebas", "Updateado el item con id: ${item.ItemCode}")
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en get update item por id ${item.ItemCode}")
            }
    }

    fun deleteItemById(itemId: String) {
        database
            .collection(this.coleccion)
            .document(itemId)
            .delete()
            .addOnSuccessListener {
                Log.e("Pruebas", "Borrado el item con id: $itemId")
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en delete item por id $it")
            }
    }

 */


    val realm = DatabaseInitializer.realm

    override fun insert(data: Any) {
        val item = data as Item

        realm.writeBlocking {
            copyToRealm(item)
        }
    }

    override fun getObjectById(id: Int, callback: (Any?) -> Unit) {
        val byId =
            realm.query<Item>("idFireBase = $0", id.toString()).first().find() as Item
        callback(byId)

    }

    override fun getObjectByIdToString(id: String, callback: (Any?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getAllObject(callback: (MutableList<*>?) -> Unit) {
        val all = realm.query<Item>().find() as MutableList<*>?
        callback(all)
    }

    override suspend fun updateObjectById(data: Any) {
        val item = data as Item
        realm.query<Item>("idFireBase = $0", item.idFireBase)
            .first()
            .find()
            ?.also { oldActivity ->
                realm.write {
                    findLatest(oldActivity)?.let { it ->
                        it.idFireBase = item.idFireBase
                        it.ItemCode = item.ItemCode
                        it.itemName = item.itemName
                        it.itemType = item.itemType
                        it.mainSupplier = item.mainSupplier
                        it.itemPrice = item.itemPrice
                        it.manageSerialNumbers = item.manageSerialNumbers
                        it.autoCreateSerialNumbersOnRelease = item.autoCreateSerialNumbersOnRelease
                        it.SAP = item.SAP
                    }
                }
            }
    }

    override suspend fun deleteObjectById(id: String) {
        val deleteObejct = realm.query<Item>("idFireBase = $0", id)
        realm.writeBlocking {
            delete(deleteObejct)
        }
    }
}
