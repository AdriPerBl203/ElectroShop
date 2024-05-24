package com.AG_AP.electroshop.firebase

import com.AG_AP.electroshop.firebase.models.Item
import com.AG_AP.electroshop.firebase.models.SEIConfig
import io.realm.kotlin.delete
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
            realm.query<Item>("ItemCode = $0", itemId).first().find() as Item
        callback(byId)
    }

    fun getAllItems(callback: (MutableList<Item>) -> Unit) {
        val all = realm.query<Item>().find()
        callback(all.toMutableList())
    }

    suspend fun updateItemById(item: Item) {

        realm.query<Item>("ItemCode = $0", item.idFireBase)
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

    suspend fun deleteItemById(itemId: String) {
        val deleteObejct = realm.query<Item>("ItemCode == $0", itemId).find().firstOrNull()
        if (deleteObejct != null) {
            realm.write {
                delete(deleteObejct)
            }
        }
    }

    fun deleteAll() {
        realm.writeBlocking {
            delete<Item>()
        }
    }


}
