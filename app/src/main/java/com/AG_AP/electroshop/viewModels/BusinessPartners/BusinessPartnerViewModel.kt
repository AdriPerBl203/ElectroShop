package com.AG_AP.electroshop.viewModels.BusinessPartners

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.AG_AP.electroshop.firebase.BusinessPartnerCRUD
import com.AG_AP.electroshop.firebase.OrderCRUD
import com.AG_AP.electroshop.firebase.models.BusinessPartner
import com.AG_AP.electroshop.uiState.BusinessPartners.BusinessPartnerUiState
import com.AG_AP.electroshop.viewModels.ActionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BusinessPartnerViewModel : ViewModel(), ActionViewModel {

    private val _uiState = MutableStateFlow(BusinessPartnerUiState())
    val uiState: StateFlow<BusinessPartnerUiState> = _uiState.asStateFlow()

    init {
        val id: String = _uiState.value.CardCode
        if (id.isNotEmpty()) {
            find()
        }

        search(true, true) { it ->
            _uiState.update { currentState ->
                currentState.copy(
                    BPSapList = it
                )
            }
        }
        search(false, true) { it ->
            _uiState.update { currentState ->
                currentState.copy(
                    BPDeviceList = it
                )
            }
        }

    }

    fun refresh() {
        val id: String = _uiState.value.CardCode
        if (id.isNotEmpty() && !_uiState.value.update) {
            find()
        }

        if (_uiState.value.FilterByName != "") {
            search(true, false) {
                _uiState.update { currentState ->
                    currentState.copy(
                        BPSapList = it
                    )
                }
            }
            search(false, false) {
                _uiState.update { currentState ->
                    currentState.copy(
                        BPDeviceList = it
                    )
                }
            }
        } else if (_uiState.value.FilterByName == "") {
            search(true, true) {
                _uiState.update { currentState ->
                    currentState.copy(
                        BPSapList = it
                    )
                }
            }
            search(false, true) {
                _uiState.update { currentState ->
                    currentState.copy(
                        BPDeviceList = it
                    )
                }
            }
        }


    }

    fun changeUpdate(boolean: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                update = boolean
            )
        }
    }

    fun changeCardCode(it: String) {
        _uiState.update { currentState ->
            currentState.copy(
                CardCode = it
            )
        }
    }

    fun changeCardType(it: String) {
        _uiState.update { currentState ->
            currentState.copy(
                CardType = it
            )
        }
    }

    fun changeCardName(it: String) {
        _uiState.update { currentState ->
            currentState.copy(
                CardName = it
            )
        }
    }

    fun changeCellular(it: String) {
        _uiState.update { currentState ->
            currentState.copy(
                Cellular = it
            )
        }
    }

    fun changeFilter(it: String) {
        _uiState.update { currentState ->
            currentState.copy(
                FilterByName = it
            )
        }
    }

    fun changeEmailAddress(it: String) {
        _uiState.update { currentState ->
            currentState.copy(
                EmailAddress = it
            )
        }
    }

    fun changeOption(it: String) {
        _uiState.update { currentState ->
            currentState.copy(
                Option = it
            )
        }
    }

    fun changeDeviceList(list: List<BusinessPartner?>) {
        _uiState.update { currentState ->
            currentState.copy(
                BPDeviceList = list
            )
        }
    }

    fun changeSAPList(list: List<BusinessPartner?>) {
        _uiState.update { currentState ->
            currentState.copy(
                BPSapList = list
            )
        }
    }

    fun search(sap: Boolean, deviceOrSap: Boolean, callback: (List<BusinessPartner?>) -> Unit) {
        if (deviceOrSap) {
            BusinessPartnerCRUD.getBPBySAP(sap) {
                callback(it)
            }
        } else {
            BusinessPartnerCRUD.getBPByName(_uiState.value.FilterByName, sap) {
                callback(it)
            }
        }

    }

    fun replaceData(it: BusinessPartner?) {
        if (it != null) {
            var cardType: String = "Cliente"
            when (it.CardType) {
                "cSupplier" -> cardType = "Proveedor"
                "cLead" -> cardType = "Lead"
                "cCustomer" -> cardType = "Cliente"

            }
            _uiState.update { currentState ->
                currentState.copy(
                    CardCode = it.CardCode,
                    CardName = it.CardName,
                    Cellular = it.Cellular,
                    EmailAddress = it.EmailAddress,
                    CardType = cardType
                )
            }
        }
    }

    override fun save(data: Boolean) {
        var CardCode = _uiState.value.CardCode
        var CardType = _uiState.value.CardType
        var CardName = _uiState.value.CardName
        var EmailAddress = _uiState.value.EmailAddress
        val Cellular = _uiState.value.Cellular
        val dataAux: BusinessPartner =
            BusinessPartner().apply {
                this.CardCode = CardCode
                this.CardType = CardType
                this.CardName = CardName
                this.Cellular = Cellular
                this.EmailAddress = EmailAddress
                this.SAP = false
            }
        var text = "Nuevo cliente añadido"
        viewModelScope.launch {
            try {
                if (!validateEmail(EmailAddress) && EmailAddress.isNotEmpty()) {
                    throw RuntimeException()
                }
                BusinessPartnerCRUD.insert(dataAux)
            } catch (e: Exception) {
                println(e.message)
                text = "Hubo un error con la creación del cliente."
            }
            println("aaa")
            _uiState.update { currentState ->
                currentState.copy(
                    message = true,
                    text = text
                )
            }
            if (!data) {
                _uiState.update { currentState ->
                    currentState.copy(
                        message = true,
                        text = text,
                        CardCode = "",
                        CardName = "",
                        Cellular = "",
                        EmailAddress = "",
                        CardType = "Cliente"
                    )
                }
            }
        }
    }

    override fun update() {
        var CardCode = _uiState.value.CardCode
        var CardType = _uiState.value.CardType
        var CardName = _uiState.value.CardName
        var EmailAddress = _uiState.value.EmailAddress
        val Cellular = _uiState.value.Cellular
        val data: BusinessPartner =
            BusinessPartner().apply {
                this.CardCode = CardCode
                this.CardType = CardType
                this.CardName = CardName
                this.Cellular = Cellular
                this.EmailAddress = EmailAddress
                this.SAP = false
            }
        var text = "Actividad actualizada"
        viewModelScope.launch {
            try {
                if (!validateEmail(EmailAddress) && EmailAddress.isNotEmpty()) {
                    throw RuntimeException()
                }
                BusinessPartnerCRUD.updateObjectById(data)
            } catch (e: Exception) {
                println(e.message)
                text = "Hubo un error con la actualizacion del cliente."
            }
            println("aaa")
            _uiState.update { currentState ->
                currentState.copy(
                    message = true,
                    text = text
                )
            }
        }
    }

    override fun delete() {
        val id = _uiState.value.CardCode
        var text = "Cliente eliminado"
        viewModelScope.launch {
            try {
                BusinessPartnerCRUD.deleteObjectById(id)
            } catch (e: Exception) {
                println(e.message)
                text = "Hubo un error borrando el cliente."
            }
            _uiState.update { currentState ->
                currentState.copy(
                    message = true,
                    text = text,
                    CardCode = "",
                    CardName = "",
                    Cellular = "",
                    EmailAddress = "",
                    CardType = "Cliente"
                )
            }
        }
    }

    override fun find() {
        if (_uiState.value.CardCode.isEmpty()) {
            _uiState.update { currentState ->
                currentState.copy(
                    message = true,
                    text = "Formato no válido"
                )
            }
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            BusinessPartnerCRUD.getObjectByIdToString(_uiState.value.CardCode) { dataAux ->
                if (dataAux != null && dataAux is BusinessPartner) {
                    var cardType: String = ""
                    when (dataAux.CardType) {
                        "cSupplier" -> cardType = "Proveedor"
                        "cLead" -> cardType = "Lead"
                        "cCustomer" -> cardType = "Cliente"

                    }
                    _uiState.update { currentState ->
                        currentState.copy(
                            CardCode = dataAux.CardCode,
                            CardType = cardType,
                            CardName = dataAux.CardName,
                            Cellular = dataAux.Cellular,
                            EmailAddress = dataAux.EmailAddress,
                            update = true
                        )
                    }
                } else {
                    _uiState.update { currentState ->
                        currentState.copy(
                            message = true,
                            text = "Cliente no encontrada con número: ${_uiState.value.CardCode}"
                        )
                    }
                }
            }
        }
    }

    fun checkOption(navController: NavController) {
        val it = _uiState.value.Option

        when (it) {
            "Añadir y ver" -> save(true)
            "Añadir y nuevo" -> save(false)
            "Añadir y salir" -> {
                save(false)
                navController.popBackStack()
            }

            "Actualizar" -> update()
            "Borrar" -> delete()
            else -> ""
        }
    }

    fun changeActionButton(it: String) {
        _uiState.update { currentState ->
            currentState.copy(
                Option = it
            )
        }
    }

    override fun menssageFunFalse() {
        _uiState.update { currentState ->
            currentState.copy(
                message = false
            )
        }
    }

    fun validateEmail(email: String): Boolean {
        val pattern = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$")
        return pattern.matches(email)
    }

    fun showDialogfilterItem(){

        val cardCode = _uiState.value.CardCode

        if (cardCode.isNotEmpty()) {

            OrderCRUD.getAllObjectOrdeByCarCodeAndDocDueDate(cardCode){ list ->
                val listAux: MutableList<String> = mutableListOf()
                if (list != null) {
                    for(x in list){
                        for(y in x.DocumentLines){
                            listAux+= y.ItemCode
                        }
                    }
                    val listWithoutDuplicates: MutableList<String> = listAux.distinct().toMutableList()
                    _uiState.update { currentState ->
                        currentState.copy(
                            showDialogFilter = true,
                            ItemBPList = listWithoutDuplicates
                        )
                    }
                }
            }

        }else{
            _uiState.update { currentState ->
                currentState.copy(
                    showDialogFilter = true
                )
            }
        }
    }

    fun closeDialogfilterItem(){
        _uiState.update { currentState ->
            currentState.copy(
                showDialogFilter = false
            )
        }
    }

}