package com.AG_AP.electroshop.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AG_AP.electroshop.firebase.ActivityCRUD
import com.AG_AP.electroshop.firebase.BusinessPartnerCRUD
import com.AG_AP.electroshop.firebase.models.Activity
import com.AG_AP.electroshop.firebase.models.BusinessPartner
import com.AG_AP.electroshop.uiState.BusinessPartnerUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BusinessPartnerViewModel : ViewModel(),ActionViewModel {

    private val _uiState = MutableStateFlow(BusinessPartnerUiState())
    val uiState: StateFlow<BusinessPartnerUiState> = _uiState.asStateFlow()

    fun changeCardCode(it: String) {
        _uiState.update { currentState -> currentState.copy(
            CardCode = it
        ) }
    }
    fun changeCardType(it: String) {
        _uiState.update { currentState -> currentState.copy(
            CardType = it
        ) }
    }
    fun changeCardName(it: String) {
        _uiState.update { currentState -> currentState.copy(
            CardName = it
        ) }
    }
    fun changeCellular(it: String) {
        _uiState.update { currentState -> currentState.copy(
            Cellular = it
        ) }
    }
    fun changeEmailAddress(it: String) {
        _uiState.update { currentState -> currentState.copy(
            EmailAddress = it
        ) }
    }

    override fun guardar(data: Boolean) {
        var CardCode = _uiState.value.CardCode
        var CardType = _uiState.value.CardType
        var CardName = _uiState.value.CardName
        var EmailAddress = _uiState.value.EmailAddress
        val Cellular = _uiState.value.Cellular
        val dataAux:BusinessPartner = BusinessPartner(CardCode,CardType,CardName,EmailAddress,Cellular)
        var text ="Nuevo cliente añadida"
        viewModelScope.launch {
            try{
                BusinessPartnerCRUD.insert(dataAux)
            }catch (e:Exception){
                println(e.message)
                text= "Hubo un error con la creación del cliente."
            }
            println("aaa")
            _uiState.update { currentState -> currentState.copy(
                message = true,
                text = text
            ) }
            if(!data){
                _uiState.update { currentState -> currentState.copy(
                    message = true,
                    text = text,
                    CardCode="",
                    CardName = "",
                    Cellular ="",
                    EmailAddress ="",
                    CardType = "Cliente"
                ) }
            }
        }
    }

    override fun update() {
        var CardCode = _uiState.value.CardCode
        var CardType = _uiState.value.CardType
        var CardName = _uiState.value.CardName
        var EmailAddress = _uiState.value.EmailAddress
        val Cellular = _uiState.value.Cellular
        val data:BusinessPartner = BusinessPartner(CardCode,CardType,CardName,EmailAddress,Cellular)
        var text ="Actividad actualizada"
        viewModelScope.launch {
            try{
                BusinessPartnerCRUD.updateObjectById(data)
            }catch (e:Exception){
                println(e.message)
                text= "Hubo un error con la actuzalicacion del cliente."
            }
            println("aaa")
            _uiState.update { currentState -> currentState.copy(
                message = true,
                text = text
            ) }
        }
    }

    override fun borrar() {
        val id = _uiState.value.CardCode
        var text ="Cliente eliminado"
        viewModelScope.launch {
            try{
                BusinessPartnerCRUD.deleteObjectById(id)
            }catch (e:Exception){
                println(e.message)
                text= "Hubo un error borrando el cliente."
            }
            _uiState.update { currentState -> currentState.copy(
                message = true,
                text = text,
                CardCode="",
                CardName = "",
                Cellular ="",
                EmailAddress ="",
                CardType = "Cliente"
            ) }
        }
    }

    override fun find() {
        if(_uiState.value.CardCode.isEmpty()){
            _uiState.update { currentState -> currentState.copy(
                message = true,
                text = "Formato no válido"
            ) }
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            BusinessPartnerCRUD.getObjectById(_uiState.value.CardCode.toInt()){ dataAux ->

                if(dataAux != null && dataAux is BusinessPartner){
                    _uiState.update { currentState -> currentState.copy(
                        CardCode = dataAux.CardCode,
                        CardType = dataAux.CardType,
                        CardName = dataAux.CardName,
                        Cellular= dataAux.Cellular,
                        EmailAddress= dataAux.EmailAddress
                    ) }
                }else{
                    _uiState.update { currentState -> currentState.copy(
                        message = true,
                        text = "Cliente no encontrada con número: ${_uiState.value.CardCode}"
                    ) }
                }
            }
        }
    }

    override fun menssageFunFalse() {
        _uiState.update { currentState -> currentState.copy(
            message = false
        ) }
    }
}