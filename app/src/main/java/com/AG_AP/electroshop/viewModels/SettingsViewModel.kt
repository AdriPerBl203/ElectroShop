package com.AG_AP.electroshop.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AG_AP.electroshop.endpoints.models.login.Login
import com.AG_AP.electroshop.endpoints.objects.ActivityObj
import com.AG_AP.electroshop.endpoints.objects.BusinessPartnersObj
import com.AG_AP.electroshop.endpoints.objects.ItemObj
import com.AG_AP.electroshop.endpoints.objects.LoginObj
import com.AG_AP.electroshop.endpoints.objects.OrdersObj
import com.AG_AP.electroshop.endpoints.objects.PurchaseOrdersObj
import com.AG_AP.electroshop.functions.Config
import com.AG_AP.electroshop.functions.validarURL
import com.AG_AP.electroshop.uiState.SettingUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
class SettingsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SettingUiState())
    val uiState: StateFlow<SettingUiState> = _uiState.asStateFlow()

    fun urlExt(data: String){
        _uiState.update { currentState -> currentState.copy(
            urlExt = data
        ) }
    }

    fun urlInt(data: String){
        _uiState.update { currentState -> currentState.copy(
            urlInt = data
        ) }
    }

    fun changeUrlExt(it: String) {
        _uiState.update { currentState -> currentState.copy(
            urlExt = it
        ) }
    }

    fun changeUrlInt(it: String) {
        _uiState.update { currentState -> currentState.copy(
            urlInt = it
        ) }
    }

    fun changeDataBase(it: String) {
        _uiState.update { currentState -> currentState.copy(
            dataBase = it
        ) }
    }

    fun changeUrlUser(it: String) {
        _uiState.update { currentState -> currentState.copy(
            login = it
        ) }
    }

    fun changeUrlPass(it: String) {
        _uiState.update { currentState -> currentState.copy(
            password = it
        ) }
    }

    fun menssageFun(){
        var urlInt = _uiState.value.urlInt
        var urlExt = _uiState.value.urlExt
        var login = _uiState.value.login
        var password = _uiState.value.password
        val dataBase = _uiState.value.dataBase
        var textShow = ""

        if(urlInt.isEmpty() || urlExt.isEmpty() || login.isEmpty() || password.isEmpty() || dataBase.isEmpty()){
            textShow = "Todos los campos deben de estar rellenos."
            _uiState.update { currentState -> currentState.copy(
                message = true,
                text = textShow
            ) }
            return;
        }

        if(!validarURL(urlInt) || !validarURL(urlExt)){
            textShow = "Campos de las URL no validos."
            _uiState.update { currentState -> currentState.copy(
                message = true,
                text = textShow
            ) }
            return;
        }

        /*Hacer la conexió*/
        viewModelScope.launch {
            _uiState.update { currentState -> currentState.copy(
                progress = true,
            ) }
            val dataLogin = Login(dataBase,password,login)
            //val data = LoginObj.loginAccess(dataLogin,urlInt)
            val data = LoginObj.loginAcessTwoversion(dataLogin,urlInt)
            var text:String=""
            if(data){
                //pruebas para traer articulos, clientes, pedidos,
                val items = ItemObj.getItems(Config.rulUse)
                val BusinessPartners = BusinessPartnersObj.getBusinessPartners(Config.rulUse)
                val activities = ActivityObj.getActivities(Config.rulUse)
                val orders = OrdersObj.getOrders(Config.rulUse)
                val pruchaseOrders = PurchaseOrdersObj.getPurchaseOrders(Config.rulUse)
                println("")
                //fin pruebas
            }else{
                text ="Conexión NO realizada"
            }
            _uiState.update { currentState -> currentState.copy(
                message = true,
                text = text,
                progress = false
            ) }
        }
    }

    fun menssageFunFalse(){
        _uiState.update { currentState -> currentState.copy(
            message = false
        ) }
    }
}