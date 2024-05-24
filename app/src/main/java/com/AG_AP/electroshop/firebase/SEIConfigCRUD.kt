package com.AG_AP.electroshop.firebase

import android.annotation.SuppressLint
import android.util.Log
import com.AG_AP.electroshop.firebase.models.Activity
import com.AG_AP.electroshop.firebase.models.OrderFireBase
import com.AG_AP.electroshop.firebase.models.SEIConfig
import io.realm.kotlin.delete
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults

object SEIConfigCRUD {

    val realm = DatabaseInitializer.realm

    fun insertSEIConfig(config: SEIConfig) {
        realm.writeBlocking {
            copyToRealm(config)
        }
    }


    fun getSEIConfigById(Code: String, callback: (SEIConfig?) -> Unit) {
        val byId =
            realm.query<SEIConfig>("Code = $0", Code).find().first()
        callback(byId)

    }

    fun getSEIConfigByName(Name: String, callback: (SEIConfig?) -> Unit) {
        val byId =
            realm.query<SEIConfig>("U_name = $0", Name).find().first()
        callback(byId)

    }

    fun getAllSEIConfig(callback: (MutableList<SEIConfig>?) -> Unit) {
        val all = realm.query<SEIConfig>().find()  as RealmResults<SEIConfig>?
        callback(all?.toMutableList())
    }

     fun deleteSEIConfigById(idConfig: Int) {
         val deleteObejct = realm.query<SEIConfig>("Code == $0", idConfig).find().firstOrNull()
         realm.writeBlocking {
             if (deleteObejct != null) {
                 findLatest(deleteObejct)
                     ?.also { delete(it) }
             }
         }
    }

    fun deleteAll() {
        realm.writeBlocking {
            delete<SEIConfig>()
        }
    }

    /*fun getU_nameTheUser():MutableList<String?>{
        var uNameList = mutableListOf<String?>()
        this.getAllSEIConfig { element->
            if(element is MutableList<SEIConfig>){
                element.forEach { element->
                    uNameList.add(element.U_name)
                }
            }
        }
        return uNameList
    }*/



}