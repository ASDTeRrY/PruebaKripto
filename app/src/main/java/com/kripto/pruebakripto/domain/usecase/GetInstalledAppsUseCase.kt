package com.kripto.pruebakripto.domain.usecase

import com.kripto.pruebakripto.data.database.entity.AppInfoEntity
import com.kripto.pruebakripto.data.repository.AppRepository
import javax.inject.Inject

class GetInstalledAppsUseCase @Inject constructor(
    private val appRepository: AppRepository
) {
    suspend operator fun invoke(): List<AppInfoEntity>  =  appRepository.getInstalledApps()
}