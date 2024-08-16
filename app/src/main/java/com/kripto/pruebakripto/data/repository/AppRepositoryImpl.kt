package com.kripto.pruebakripto.data.repository

import android.app.ActivityManager
import android.app.usage.StorageStatsManager
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Build
import android.os.storage.StorageManager
import androidx.annotation.RequiresApi
import com.kripto.pruebakripto.data.database.entity.AppInfo
import java.io.File
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val packageManager: PackageManager,
    private val context: Context
) : AppRepository {
    override fun getInstalledApps(): List<AppInfo> {
        val intent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        val apps = packageManager.queryIntentActivities(intent, 0)
        return apps.map { resolveInfo -> getAppInfo(resolveInfo) }
    }


    private fun getAppInfo(resolveInfo: ResolveInfo): AppInfo {
        val packageManager = context.packageManager
        val packageName = resolveInfo.activityInfo.packageName

        val memoryUsage = getMemoryUsage(packageName)
        val storageUsage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getStorageUsage(packageName)
        } else "0.00"
        val usageStats = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getAppUsageStats(packageName)
        } else null

        val permissions = getAppPermissions(packageName)
        val installTime = getAppInstallTime(packageName)
        val lastUpdateTime = getAppLastUpdateTime(packageName)
        val isSystemApp = (resolveInfo.activityInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0
        val lastUsedFormatted = usageStats?.lastTimeUsed?.let {
            formatDate(it)
        } ?: "N/A"

        val installTimeFormatted = formatDate(installTime)
        val lastUpdateTimeFormatted = formatDate(lastUpdateTime)
        return AppInfo(
            name = resolveInfo.loadLabel(packageManager).toString(),
            packageName = packageName,
            memoryUsage = memoryUsage,
            storageUsage = storageUsage,
            frequencyOfUse = usageStats?.totalTimeInForeground?.toInt() ?: 0,
            totalTimeInForeground =  usageStats?.totalTimeInForeground?.let { it / 1000 } ?: 0L,
            lastUsed = lastUsedFormatted,
            permissions = permissions,
            installTime = installTimeFormatted,
            lastUpdateTime = lastUpdateTimeFormatted,
            isSystemApp = isSystemApp
        )
    }

    private fun getMemoryUsage(packageName: String): String {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        val usedMemory = memoryInfo.totalMem - memoryInfo.availMem
        val usedMemoryMB = usedMemory.toDouble() / (1024 * 1024) // Convertir a MB
        val decimalFormat = DecimalFormat("#.##")
        return decimalFormat.format(usedMemoryMB)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getStorageUsage(packageName: String): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(packageName, 0)
            val appFile = File(packageInfo.applicationInfo.sourceDir)
            val storageUsageBytes = appFile.length()
            val storageUsageMB = storageUsageBytes.toDouble() / (1024 * 1024) // Convertir a MB
            val decimalFormat = DecimalFormat("#.##")
            decimalFormat.format(storageUsageMB)
        } catch (e: Exception) {
            e.printStackTrace()
            "0.00"
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getAppUsageStats(packageName: String): UsageStats? {
        val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val currentTime = System.currentTimeMillis()
        val stats = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            currentTime - 1000 * 60 * 60 * 24, // Últimas 24 horas
            currentTime
        )
        return stats.find { it.packageName == packageName }
    }

    private fun getAppPermissions(packageName: String): List<String> {
        return packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS).requestedPermissions?.toList() ?: emptyList()
    }

    private fun getAppInstallTime(packageName: String): Long {
        return packageManager.getPackageInfo(packageName, 0).firstInstallTime
    }

    private fun getAppLastUpdateTime(packageName: String): Long {
        return packageManager.getPackageInfo(packageName, 0).lastUpdateTime
    }

    private fun formatDate(timestamp: Long): String {
        val date = Date(timestamp)
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return format.format(date)
    }
}