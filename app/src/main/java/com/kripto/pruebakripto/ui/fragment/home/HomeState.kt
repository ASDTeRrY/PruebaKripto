package com.kripto.pruebakripto.ui.fragment.home

import com.kripto.pruebakripto.data.database.entity.AppInfo


sealed class HomeState {
    data object Loading : HomeState()
    data class Success(val products: List<AppInfo>) : HomeState()
    data class Error(val message: String) : HomeState()
}