package com.kripto.pruebakripto.data.repository

import com.kripto.pruebakripto.data.database.entity.AppInfoEntity


interface AppRepository {
    suspend fun getInstalledApps(): List<AppInfoEntity>
    suspend fun deleteAppInfo(id: Int): Boolean
}