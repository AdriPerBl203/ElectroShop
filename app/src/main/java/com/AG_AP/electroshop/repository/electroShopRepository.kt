package com.AG_AP.electroshop.repository

import com.AG_AP.electroshop.modelsDataBase.Actividad
import com.AG_AP.electroshop.room.ElectroshopDataBaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class electroShopRepository @Inject constructor(private val electroshopDataBaseDao: ElectroshopDataBaseDao) {

    suspend fun addActividad(crono: Actividad) = electroshopDataBaseDao.insert(crono)
    suspend fun updateActividad(crono: Actividad) = electroshopDataBaseDao.update(crono)
    suspend fun deleteActividad(crono: Actividad) = electroshopDataBaseDao.delete(crono)
    fun getAllActividad(): Flow<List<Actividad>> = electroshopDataBaseDao.getActividad().flowOn(Dispatchers.IO).conflate()
    fun getActividadById(id:Int): Flow<Actividad> = electroshopDataBaseDao.getActividadById(id).flowOn(Dispatchers.IO).conflate()

}