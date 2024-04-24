package com.AG_AP.electroshop.di

import android.content.Context
import androidx.room.Room
import com.AG_AP.electroshop.room.ElectroShopDataBase
import com.AG_AP.electroshop.room.ElectroshopDataBaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModulo {

    @Singleton
    @Provides
    fun providesElectroShopDao(electroShopDatabase: ElectroShopDataBase) : ElectroshopDataBaseDao {
        return electroShopDatabase.electroShopDao()
    }

    @Singleton
    @Provides
    fun providesElectroShopDatabase(@ApplicationContext context : Context): ElectroShopDataBase {
        return Room.databaseBuilder(
            context,
            ElectroShopDataBase::class.java, "electroShop_db"
        ).fallbackToDestructiveMigration()
            .build()
    }
}