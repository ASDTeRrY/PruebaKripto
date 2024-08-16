package com.kripto.pruebakripto.data.database.entity

data class AppInfo(
    val name: String,
    val packageName: String,
    val memoryUsage: String,  // Uso de memoria en bytes
    val storageUsage: String,  // Uso de almacenamiento en bytes
    val frequencyOfUse: Int,  // Frecuencia de uso
    val totalTimeInForeground: Long,  // Tiempo total en primer plano
    val lastUsed: String,  // Última vez que se usó
    val permissions: List<String>,  // Permisos solicitados
    val installTime: String,  // Fecha de instalación
    val lastUpdateTime: String,  // Fecha de última actualización
    val isSystemApp: Boolean  // Es una aplicación del sistema
)
