package com.AG_AP.electroshop.viewModels.Activities


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AG_AP.electroshop.firebase.ActivityCRUD
import com.AG_AP.electroshop.firebase.BusinessPartnerCRUD
import com.AG_AP.electroshop.firebase.OrderCRUD
import com.AG_AP.electroshop.firebase.PurchaseOrderCRUD
import com.AG_AP.electroshop.firebase.models.Activity
import com.AG_AP.electroshop.firebase.models.BusinessPartner
import com.AG_AP.electroshop.firebase.models.OrderFireBase
import com.AG_AP.electroshop.uiState.Activities.ActivityUiState
import com.AG_AP.electroshop.viewModels.ActionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ActivityViewModel : ViewModel(), ActionViewModel {

    private val _uiState = MutableStateFlow(ActivityUiState())
    val uiState: StateFlow<ActivityUiState> = _uiState.asStateFlow()

    init {
        val id:String = _uiState.value.ClgCode
        if(id.isNotEmpty()){
            find()
        }

        //order
        OrderCRUD.getAllObject { list ->
            val mutableList = list as? MutableList<OrderFireBase>
            mutableList?.let {
                _uiState.update { currentState -> currentState.copy(
                    ListOrders = it.toList()
                ) }
            }
        }

        //purchaseOrder
        PurchaseOrderCRUD.getAllObject { list ->
            val mutableList = list as? MutableList<OrderFireBase>
            mutableList?.let {
                _uiState.update { currentState -> currentState.copy(
                    ListPurchaseOrders = it.toList()
                ) }
            }
        }

        //BusinessPartner
        BusinessPartnerCRUD.getAllObject { list ->
            val mutableList = list as? MutableList<BusinessPartner>
            mutableList?.let {
                _uiState.update { currentState -> currentState.copy(
                    ListBusinessPartner = it.toList()
                ) }
            }
        }
    }

    fun refreshScreen() {
        val id: String = _uiState.value.ClgCode
        if (id.isNotEmpty()) {
            find()
        }
    }

    //cambiar en los campo
    fun changenota(it: String) {
        _uiState.update { currentState -> currentState.copy(
            nota = it
        ) }
    }
    fun changeActivityDate(it: String) {
        _uiState.update { currentState -> currentState.copy(
            ActivityDate = it
        ) }
    }
    fun changeActivityTime(it: String) {
        _uiState.update { currentState -> currentState.copy(
            ActivityTime = it
        ) }
    }
    fun changeCardCode(it: String) {
        _uiState.update { currentState -> currentState.copy(
            CardCode = it
        ) }
    }
    fun changeEndTime(it: String) {
        _uiState.update { currentState -> currentState.copy(
            EndTime = it
        ) }
    }

    fun changeAction(it: String) {
        _uiState.update { currentState -> currentState.copy(
            Action = it
        ) }
    }

    fun changeTel(it: String) {
        _uiState.update { currentState -> currentState.copy(
            Tel = it
        ) }
    }

    fun changeClgCode(it: String) {
        _uiState.update { currentState -> currentState.copy(
            ClgCode = it
        ) }
    }

    fun changePriority(it: String) {
        _uiState.update { currentState -> currentState.copy(
            Priority = it
        ) }
    }

    fun changePedidoCompra(it: String) {
        _uiState.update { currentState -> currentState.copy(
            U_SEIPEDIDOCOMPRAS = it
        ) }
    }

    fun changePedidoCliente(it: String) {
        _uiState.update { currentState -> currentState.copy(
            U_SEIPEDIDOCLIENTE = it
        ) }
    }

    override fun update() {
        var nota = _uiState.value.nota
        var ActivityDate = _uiState.value.ActivityDate
        var ActivityTime = _uiState.value.ActivityTime
        var CardCode = _uiState.value.CardCode
        val EndTime = _uiState.value.EndTime
        val Action = _uiState.value.Action
        val Tel = _uiState.value.Tel
        val ClgCode = _uiState.value.ClgCode
        val Priority = _uiState.value.Priority
        val pedidoCliente = _uiState.value.U_SEIPEDIDOCLIENTE.toInt()
        val pedidoCompra = _uiState.value.U_SEIPEDIDOCOMPRAS.toInt()
        val Activity = Activity("",nota,ActivityDate,ActivityTime,CardCode,EndTime,Action,Tel,ClgCode,Priority,pedidoCompra,pedidoCliente,false)
        var text ="Actividad actualizada"
        viewModelScope.launch {
            try{
                ActivityCRUD.updateActivityById(Activity)
            }catch (e:Exception){
                println(e.message)
                text= "Hubo un error con la actuzalicacion de la actividad."
            }
            println("aaa")
            _uiState.update { currentState -> currentState.copy(
                message = true,
                text = text
            ) }
        }
    }

    override fun delete() {
        val ClgCode = _uiState.value.ClgCode
        var text ="Actividad eliminada"
        viewModelScope.launch {
            try{
                ActivityCRUD.deleteActivityById(ClgCode)
            }catch (e:Exception){
                println(e.message)
                text= "Hubo un error con el borrado de la actividad."
            }
            _uiState.update { currentState -> currentState.copy(
                message = true,
                text = text,
                nota = "",
                ActivityDate ="",
                ActivityTime ="",
                CardCode="",
                EndTime="",
                Action="Llamada telefónica",
                Priority = "Normal",
                Tel = "",
                ClgCode = "",
                U_SEIPEDIDOCLIENTE = "",
                U_SEIPEDIDOCOMPRAS = ""
            ) }
        }
    }

    override fun find() {
        if(_uiState.value.ClgCode.isEmpty()){
            _uiState.update { currentState -> currentState.copy(
                message = true,
                text = "Formato no válido"
            ) }
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            ActivityCRUD.getActivityById(_uiState.value.ClgCode.toInt()){Activity ->
                if(Activity != null){
                    _uiState.update { currentState -> currentState.copy(
                        nota = Activity.nota,
                        ActivityDate = Activity.ActivityDate,
                        ActivityTime = Activity.ActivityTime,
                        CardCode= Activity.CardCode,
                        EndTime= Activity.EndTime,
                        Action= Activity.Action,
                        Priority = Activity.Priority,
                        Tel = Activity.Tel,
                        ClgCode = Activity.ClgCode,
                        U_SEIPEDIDOCLIENTE = Activity.U_SEIPEDIDOCLIENTE.toString(),
                        U_SEIPEDIDOCOMPRAS = Activity.U_SEIPEDIDOCOMPRAS.toString()
                    ) }
                }else{
                    _uiState.update { currentState -> currentState.copy(
                        message = true,
                        text = "Actividad no encontrada con número: ${_uiState.value.ClgCode}"
                    ) }
                }
            }
        }
    }

    override fun save(persistencia:Boolean) {
        var nota = _uiState.value.nota ?: ""
        var ActivityDate = _uiState.value.ActivityDate ?: ""
        var ActivityTime = _uiState.value.ActivityTime ?: ""
        var CardCode = _uiState.value.CardCode ?: ""
        val EndTime = _uiState.value.EndTime ?: ""
        var Action = _uiState.value.Action ?: ""
        //'cn_Conversation'-'C', 'cn_Meeting'-'M', 'cn_Task'-'T', 'cn_Other'-'N', 'cn_Note'-'E', 'cn_Campaign'-'P'
        when(Action){
            "Llamada telefónica"-> Action= "cn_Conversation"
            "Reunión"->Action= "cn_Meeting"
            "Tarea"->    Action= "cn_Task"
            "Nota"-> Action= "cn_Note"
            "Campaña"->Action= "cn_Campaign"
            "Otros"->    Action= "cn_Other"
        }
        val Tel = _uiState.value.Tel ?: ""
        val ClgCode = _uiState.value.ClgCode ?: ""
        var Priority = _uiState.value.Priority ?: ""
        //The valid names are: 'pr_Low'-'0', 'pr_Normal'-'1', 'pr_High'-'2'"
        when(Priority){
            "Bajo"-> Priority= "pr_Low"
            "Normal"->Priority= "pr_Normal"
            "Alto"->    Priority= "pr_High"
        }
        val pedidoCliente = _uiState.value.U_SEIPEDIDOCLIENTE.toInt() ?: 0
        val pedidoCompra = _uiState.value.U_SEIPEDIDOCOMPRAS.toInt() ?: 0
        val dataConcat = ActivityDate.plus("T00:00:00Z")
        val newActivity = Activity("",nota,dataConcat,ActivityTime,CardCode,EndTime,Action,Tel,ClgCode,Priority,pedidoCompra,pedidoCliente,false)
        var text ="Nueva Actividad añadida"
        viewModelScope.launch {
            try{
                ActivityCRUD.insertActivityForFireBase(newActivity)
            }catch (e:Exception){
                println(e.message)
                text= "Hubo un error con la creación de la actividad."
            }
            println("aaa")
            _uiState.update { currentState -> currentState.copy(
                message = true,
                text = text
            ) }
            if(!persistencia){
                _uiState.update { currentState -> currentState.copy(
                    nota = "",
                    ActivityDate ="",
                    ActivityTime ="",
                    CardCode="",
                    EndTime="",
                    Action="Llamada telefónica",
                    Priority = "Normal",
                    Tel = "",
                    ClgCode = "",
                    U_SEIPEDIDOCLIENTE = "",
                    U_SEIPEDIDOCOMPRAS = ""
                ) }
            }
        }
    }

    override fun menssageFunFalse() {
        _uiState.update { currentState -> currentState.copy(
            message = false
        ) }
    }

    fun showDialogPurchaseOrder(){
        _uiState.update { currentState -> currentState.copy(
            showDialogPurchaseOrder = true
        ) }
    }

    fun showDialogOrder(){
        _uiState.update { currentState -> currentState.copy(
            showDialogOrder = true
        ) }
    }

    fun showDialogBusinessPartner(){
        _uiState.update { currentState -> currentState.copy(
            showDialogBussinesPartner = true
        ) }
    }

    fun closerDialogPurchaseOrder(){
        _uiState.update { currentState -> currentState.copy(
            showDialogPurchaseOrder = false
        ) }
    }

    fun closerDialogBusinessPartner(){
        _uiState.update { currentState -> currentState.copy(
            showDialogBussinesPartner = false
        ) }
    }

    fun closerDialogOrder(){
        _uiState.update { currentState -> currentState.copy(
            showDialogOrder = false
        ) }
    }

    fun changeStartTime(data :String){
        _uiState.update { currentState -> currentState.copy(
            ActivityTime = data
        ) }
    }
}