package com.kripto.pruebakripto.data.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "AppInfo", indices = [Index(value = ["id"])])
data class AppInfoEntity(
    @PrimaryKey(autoGenerate = true)val id: Int,
    val name: String,
    val packageName: String,
    val memoryUsage: String,  // Uso de memoria en bytes
    val storageUsage: String,  // Uso de almacenamiento en bytes
    val frequencyOfUse: Int,  // Frecuencia de uso
    val totalTimeInForeground: Long,  // Tiempo total en primer plano
    val lastUsed: String,  // Última vez que se usó
    val installTime: String,  // Fecha de instalación
    val lastUpdateTime: String,  // Fecha de última actualización
    val isSystemApp: Boolean  // Es una aplicación del sistema
)
