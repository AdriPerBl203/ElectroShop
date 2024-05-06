package com.AG_AP.electroshop.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.AG_AP.electroshop.endpoints.models.activity.Activity
import com.AG_AP.electroshop.endpoints.models.activity.PostActivity
import com.AG_AP.electroshop.endpoints.models.login.Login
import com.AG_AP.electroshop.endpoints.objects.ActivityObj
import com.AG_AP.electroshop.endpoints.objects.LoginObj
import com.AG_AP.electroshop.firebase.ActivityCRUD
import com.AG_AP.electroshop.functions.Config
import com.AG_AP.electroshop.functions.SessionObj
import com.AG_AP.electroshop.uiState.MenuUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
        viewModelScope.launch() {
            ActivityCRUD.getAllActivity { list ->
                viewModelScope.launch() {
                    val dataLogin = Login(Config.dataBase, Config.password, Config.login)
                    LoginObj.loginAcessTwoversion(dataLogin, Config.rulUse)
                    for (x in list) {
                        if (!x.SAP) {

                            val data: PostActivity = PostActivity(
                                x.Action,
                                x.ActivityDate,
                                x.ActivityTime,
                                x.CardCode,
                                x.EndTime,
                                x.nota,
                                x.Priority,
                                x.U_SEIPEDIDOCLIENTE ?: 0,
                                x.U_SEIPEDIDOCOMPRAS ?: 0
                            )
                            ActivityObj.PostActivities(Config.rulUse, data)
                            //TODO
                            if(x.idFireBase !=null){
                                ActivityCRUD.deleteActivityById(x.idFireBase)
                            }
                        }
                    }
                    viewModelScope.launch(Dispatchers.IO) {
                        val activities : Activity? = ActivityObj.getActivities(Config.rulUse)
                        if(activities is Activity){
                            activities.value.forEach{element ->
                                ActivityCRUD.deleteActivityById(element.ActivityCode.toString())
                            }
                            activities.value.forEach{element ->
                                val activity : com.AG_AP.electroshop.firebase.models.Activity = com.AG_AP.electroshop.firebase.models.Activity(
                                    "",
                                    element.Notes ?: "",
                                    element.ActivityDate ?: "",
                                    element.ActivityTime ?: "",
                                    element.CardCode ?: "",
                                    element.EndTime ?: "",
                                    element.Activity ?: "",
                                    element.Notes ?: "",//
                                    element.ActivityCode.toString() ?: "",
                                    element.Priority ?: "",
                                    element.U_SEIPEDIDOCOMPRAS ?: 0,
                                    element.U_SEIPEDIDOCLIENTE ?: 0,
                                    true
                                )
                                ActivityCRUD.insertActivity(activity)
                            }
                            LoginObj.logout(Config.rulUse)
                        }
                    }
                    //LoginObj.logout(Config.rulUse)

                }
            }
        }

    }

    fun upTotal() {
        Log.e("MenuViewModel", "upTotal() method is not yet implemented")
        // Aquí deberías implementar la lógica para actualizar las actividades
    }

}