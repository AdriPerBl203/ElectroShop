package com.AG_AP.electroshop.functions

import android.content.Context
import com.google.gson.Gson
import kotlinx.coroutines.flow.update

object Config {
    var rulUse:String =""
    var login:String =""
    var password:String =""
    var dataBase:String =""

    fun initConfig(context: Context){
        val gson = Gson()
        val sharedPref = context?.getSharedPreferences("configuration", Context.MODE_PRIVATE)
        val json = sharedPref?.getString("configuration", null)
        if (!json.isNullOrEmpty()) {
            val dataConfig = gson.fromJson(json, ConfigurationApplication::class.java)
            this.login = dataConfig.login
            this.password = dataConfig.password
            this.dataBase = dataConfig.dataBase
            this.rulUse = dataConfig.url
        }
    }
}