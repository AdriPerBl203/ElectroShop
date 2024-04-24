package com.AG_AP.electroshop.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.AG_AP.electroshop.modelsDataBase.Actividad
import kotlinx.coroutines.flow.Flow

@Dao
interface ElectroshopDataBaseDao {

    @Query("SELECT * FROM actividades")
    fun getActividad(): Flow<List<Actividad>>

    @Query("SELECT * FROM actividades WHERE id = :id ")
    fun getActividadById(id: Int): Flow<Actividad>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(actividad: Actividad)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(actividad: Actividad)

    @Delete
    suspend fun delete(actividad : Actividad)
}