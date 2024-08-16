package com.kripto.pruebakripto.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.kripto.pruebakripto.data.database.entity.AppInfoEntity

@Dao
interface AppInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppInfo(client: AppInfoEntity)

    @Query("SELECT * FROM appinfo")
    suspend fun getAllAppInfo(): List<AppInfoEntity>

    @Transaction
    @Query("SELECT * FROM appinfo WHERE id = :id")
    suspend fun getAppInfoById(id: Int): AppInfoEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAppInfo(clients: List<AppInfoEntity>): List<Long>

    @Query("DELETE FROM appinfo WHERE id = :id")
    suspend fun deleteAppInfo(id: Int)
}