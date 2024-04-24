package com.AG_AP.electroshop.modelsDataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "actividades")
data class Actividad(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    @ColumnInfo(name = "Notes")
    val nota: String,
    @ColumnInfo(name = "ActivityDate")
    val ActivityDate : String,
    @ColumnInfo(name = "ActivityTime")
    val ActivityTime : String,
    @ColumnInfo(name = "CardCode")
    val CardCode : String,
    @ColumnInfo(name = "EndTime")
    val EndTime : String,
    @ColumnInfo(name = "Action")
    val Action : String,
    @ColumnInfo(name = "Tel")
    val Tel : String,
    @ColumnInfo(name = "ClgCode")
    val ClgCode : String,
    @ColumnInfo(name = "Priority")
    val Priority : String,
    @ColumnInfo(name = "U_SEIPEDIDOCOMPRAS")
    val U_SEIPEDIDOCOMPRAS : Int,
    @ColumnInfo(name = "U_SEIPEDIDOCLIENTE")
    val U_SEIPEDIDOCLIENTE : Int
)
