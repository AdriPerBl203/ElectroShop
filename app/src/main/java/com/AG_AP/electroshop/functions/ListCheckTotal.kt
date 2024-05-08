package com.AG_AP.electroshop.functions

object ListCheckTotal {

    var listaMutable: MutableList<String> = mutableListOf()

    fun resetList(){
        this.listaMutable =  mutableListOf()
    }

    fun addInfo(data:String){
        this.listaMutable += data
    }

    fun getList():MutableList<String>{
        return this.listaMutable
    }
}