package com.kripto.pruebakripto.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kripto.pruebakripto.data.database.dao.AppInfoDao
import com.kripto.pruebakripto.data.database.entity.AppInfoEntity

@Database(entities = [AppInfoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appInfoDao(): AppInfoDao

}