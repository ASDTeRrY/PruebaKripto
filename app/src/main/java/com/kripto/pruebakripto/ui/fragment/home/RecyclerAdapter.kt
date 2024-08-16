package com.kripto.pruebakripto.ui.fragment.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kripto.pruebakripto.data.database.entity.AppInfoEntity
import com.kripto.pruebakripto.databinding.ItemBinding

class RecyclerAdapter(private val mContext: Context,  private val onItemClick: (View, AppInfoEntity) -> Unit) : ListAdapter<AppInfoEntity, RecyclerAdapter.AppInfoViewHolder>(AppInfoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppInfoViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppInfoViewHolder(binding, mContext, onItemClick)
    }

    override fun onBindViewHolder(holder: AppInfoViewHolder, position: Int) {
        val appInfo = getItem(position)
        holder.bind(appInfo)
    }

    class AppInfoViewHolder(private val binding: ItemBinding, private val context: Context,  private val onItemClick: (View, AppInfoEntity) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        fun bind(appInfo: AppInfoEntity) {
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
            binding.appIsSystemApp.text = "Es un Aplicacion del sistema: ${if(appInfo.isSystemApp) "Si" else "No"}"
            if (appInfo.isSystemApp){
                binding.appIconDelete.visibility = View.INVISIBLE
            }else binding.appIconDelete.visibility = View.VISIBLE
            binding.appIconDelete.setOnClickListener {
                onItemClick(binding.root, appInfo)
            }
        }
    }
}

class AppInfoDiffCallback : DiffUtil.ItemCallback<AppInfoEntity>() {
    override fun areItemsTheSame(oldItem: AppInfoEntity, newItem: AppInfoEntity): Boolean {
        return oldItem.packageName == newItem.packageName
    }

    override fun areContentsTheSame(oldItem: AppInfoEntity, newItem: AppInfoEntity): Boolean {
        return oldItem == newItem
    }
}