package com.kripto.pruebakripto.ui.fragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kripto.pruebakripto.domain.usecase.DeleteAppUseCase
import com.kripto.pruebakripto.domain.usecase.GetInstalledAppsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getInstalledAppsUseCase: GetInstalledAppsUseCase,
    private val deleteAppUseCase: DeleteAppUseCase
): ViewModel() {
    private var _state = MutableStateFlow<HomeState>(HomeState.Loading)
    val state: StateFlow<HomeState> = _state

    init {
        loadInstalledApps()
    }

    private fun loadInstalledApps() {
        viewModelScope.launch {
            _state.value = HomeState.Loading
            val result = withContext(Dispatchers.IO) { getInstalledAppsUseCase() }
            _state.value = HomeState.Success(result)
        }
    }
    fun delete(id: Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO) { deleteAppUseCase(id) }
            val currentOrders = (_state.value as? HomeState.Success)?.products ?: emptyList()
            _state.value = HomeState.Success(currentOrders.filter { it.id != id })
        }
    }
}