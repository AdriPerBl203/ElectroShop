package com.AG_AP.electroshop.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.AG_AP.electroshop.functions.SessionObj
import com.AG_AP.electroshop.uiState.MenuUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MenuViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MenuUiState())
    val uiState: StateFlow<MenuUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { currentState -> currentState.copy(
            username = SessionObj.name,
            articulo= SessionObj.articulo,
            actividad = SessionObj.actividad,
            pedidoCL= SessionObj.pedidoCL,
            pedidoCO= SessionObj.pedidoCO,
        ) }
    }

    fun closeSession(navController: NavHostController) {
        SessionObj.reset()
        navController.navigate(route = Routes.ScreenLogin.route)
    }

    fun viewEnd(navController: NavHostController) {
        navController.navigate(route = Routes.ScreenLogin.route)
    }

    fun closedDialog() {
        _uiState.update { currentState -> currentState.copy(
            dialog = false
        ) }
    }

    fun showDialog(textDialog: String) {
        _uiState.update { currentState -> currentState.copy(
            InfoDialog = textDialog,
            dialog = true
        ) }
    }

    fun upOrder() {
        Log.e("MenuViewModel", "upOrder() method is not yet implemented")
        // Aquí deberías implementar la lógica para actualizar los pedidos
    }

    fun upPurchaseOrders() {
        Log.e("MenuViewModel", "upPurchaseOrders() method is not yet implemented")
        // Aquí deberías implementar la lógica para actualizar las órdenes de compra
    }

    fun upBusinessPartners() {
        Log.e("MenuViewModel", "upBusinessPartners() method is not yet implemented")
        // Aquí deberías implementar la lógica para actualizar los socios comerciales
    }

    fun upItems() {
        Log.e("MenuViewModel", "upItems() method is not yet implemented")
        // Aquí deberías implementar la lógica para actualizar los ítems
    }

    fun upActivities() {
        Log.e("MenuViewModel", "upActivities() method is not yet implemented")
        // Aquí deberías implementar la lógica para actualizar las actividades
    }

    fun upTotal() {
        Log.e("MenuViewModel", "upTotal() method is not yet implemented")
        // Aquí deberías implementar la lógica para actualizar las actividades
    }

}