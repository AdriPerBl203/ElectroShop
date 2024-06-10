package com.AG_AP.electroshop.realm



interface ActionRealm {

    fun insert(data: Any)
    fun getObjectById(id: Int,callback:(Any?)->Unit)
    fun getObjectByIdToString(id: String, callback: (Any?) -> Unit)
    fun getAllObject(callback: (MutableList<*>?) -> Unit)
    suspend fun updateObjectById(data: Any)
    suspend fun deleteObjectById(id: String)
}