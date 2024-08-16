package com.kripto.pruebakripto.ui.fragment.home

import com.kripto.pruebakripto.data.database.entity.AppInfoEntity


sealed class HomeState {
    data object Loading : HomeState()
    data class Success(val products: List<AppInfoEntity>) : HomeState()
    data class Error(val message: String) : HomeState()
}