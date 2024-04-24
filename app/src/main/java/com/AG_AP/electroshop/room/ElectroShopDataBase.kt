package com.AG_AP.electroshop.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.AG_AP.electroshop.modelsDataBase.Actividad

@Database(entities = [Actividad::class], version = 2, exportSchema = true)
abstract class ElectroShopDataBase: RoomDatabase() {
    abstract fun electroShopDao() : ElectroshopDataBaseDao
}