package com.kripto.pruebakripto.ui.fragment.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kripto.pruebakripto.data.database.entity.AppInfo
import com.kripto.pruebakripto.databinding.ItemBinding

class RecyclerAdapter(private val mContext: Context) : ListAdapter<AppInfo, RecyclerAdapter.AppInfoViewHolder>(AppInfoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppInfoViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppInfoViewHolder(binding, mContext)
    }

    override fun onBindViewHolder(holder: AppInfoViewHolder, position: Int) {
        val appInfo = getItem(position)
        holder.bind(appInfo)
    }

    class AppInfoViewHolder(private val binding: ItemBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(appInfo: AppInfo) {
            val appIcon = context.packageManager.getApplicationIcon(appInfo.packageName)
            binding.appIcon.setImageDrawable(appIcon)
            binding.appName.text = appInfo.name
            binding.appPackageName.text = appInfo.packageName
            binding.appMemoryUsage.text = "Memoria Usada: ${appInfo.memoryUsage} MB"
            binding.appStorageUsage.text = "Almacenamiento usado: ${appInfo.storageUsage} MB"
            binding.appFrequencyOfUse.text = "Frecuencia de Uso: ${appInfo.frequencyOfUse}"
            binding.appTotalTimeInForeground.text = "Tiempo total en background: ${appInfo.totalTimeInForeground} "
            binding.appLastUsed.text = "Ultima ves usado: ${appInfo.lastUsed}"
            binding.appPermissions.text = "Los permisos son demasiados"
            binding.appInstallTime.text = "Fecha de instalacion: ${appInfo.installTime} "
            binding.appLastUpdateTime.text = "Ultima actualizacion: ${appInfo.lastUpdateTime}"
            binding.appIsSystemApp.text = "Es un Aplicacion del sistema: ${appInfo.isSystemApp}"
        }
    }
}

class AppInfoDiffCallback : DiffUtil.ItemCallback<AppInfo>() {
    override fun areItemsTheSame(oldItem: AppInfo, newItem: AppInfo): Boolean {
        return oldItem.packageName == newItem.packageName
    }

    override fun areContentsTheSame(oldItem: AppInfo, newItem: AppInfo): Boolean {
        return oldItem == newItem
    }
}