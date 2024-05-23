package com.AG_AP.electroshop.firebase

import android.annotation.SuppressLint
import android.util.Log
import com.AG_AP.electroshop.firebase.models.OrderFireBase
import com.AG_AP.electroshop.firebase.models.SEIConfig
import io.realm.kotlin.ext.query

object SEIConfigCRUD {

    val realm = DatabaseInitializer.realm

    fun insertSEIConfig(config: SEIConfig) {
        realm.writeBlocking {
            copyToRealm(config)
        }
    }


    fun getSEIConfigById(Code: String, callback: (SEIConfig?) -> Unit) {

        val byId =
            realm.query<SEIConfig>("Code = $0", Code).first().find() as SEIConfig
        callback(byId)

    }

    fun getAllSEIConfig(callback: (MutableList<SEIConfig>?) -> Unit) {
        val all = realm.query<SEIConfig>().find() as MutableList<SEIConfig>?
        callback(all)
    }

     fun deleteSEIConfigById(idConfig: Int) {
         val deleteObejct = realm.query<SEIConfig>("Code = $0", idConfig)
         realm.writeBlocking {
             delete(deleteObejct)
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