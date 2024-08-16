package com.kripto.pruebakripto.data.repository

import com.kripto.pruebakripto.data.database.entity.AppInfo


interface AppRepository {
    fun getInstalledApps(): List<AppInfo>
}