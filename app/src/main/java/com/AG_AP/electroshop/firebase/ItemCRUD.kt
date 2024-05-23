package com.AG_AP.electroshop.firebase

import android.annotation.SuppressLint
import android.util.Log
import com.AG_AP.electroshop.firebase.models.Activity
import com.AG_AP.electroshop.firebase.models.BusinessPartner
import com.AG_AP.electroshop.firebase.models.Item
import com.AG_AP.electroshop.firebase.models.ItemType
import com.AG_AP.electroshop.firebase.models.Price
import io.realm.kotlin.ext.query

object ItemCRUD {

    val realm = DatabaseInitializer.realm

    fun insertItem(data: Item) {
        realm.writeBlocking {
            copyToRealm(data)
        }
    }

    fun insertItemForFireBase(item: Item) {
        TODO()
    }

    fun getItemById(itemId: String, callback: (Item?) -> Unit) {
        val byId =
            realm.query<Item>("idFireBase = $0", itemId).first().find() as Item
        callback(byId)
    }

    fun getAllItems(callback: (MutableList<Item>) -> Unit) {
        val all = realm.query<Item>().find() as MutableList<Item>
        callback(all)
    }

    suspend fun updateItemById(item: Item) {

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

    fun deleteItemById(itemId: String) {
        val deleteObejct = realm.query<Item>("idFireBase = $0", itemId)
        realm.writeBlocking {
            delete(deleteObejct)
        }
    }
}
