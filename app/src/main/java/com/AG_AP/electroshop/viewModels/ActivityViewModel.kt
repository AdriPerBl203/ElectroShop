package com.AG_AP.electroshop.viewModels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AG_AP.electroshop.uiState.ActivityUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ActivityViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ActivityUiState())
    val uiState: StateFlow<ActivityUiState> = _uiState.asStateFlow()

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

    fun update() {
        /*
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
        val updateActivity = Actividad(ClgCode.toInt(),nota,ActivityDate,ActivityTime,CardCode,EndTime,Action,Tel,ClgCode,Priority,pedidoCompra,pedidoCliente)
        var text ="Actividad actualizada"
        viewModelScope.launch {
            try{
                repository.updateActividad(updateActivity)
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
        */
    }

    fun borrar() {
        /*
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
        val deleteActivity = Actividad(ClgCode.toInt(),nota,ActivityDate,ActivityTime,CardCode,EndTime,Action,Tel,ClgCode,Priority,pedidoCompra,pedidoCliente)
        var text ="Actividad eliminada"
        viewModelScope.launch {
            try{
                repository.deleteActividad(deleteActivity)
            }catch (e:Exception){
                println(e.message)
                text= "Hubo un error con la actuzalicacion de la actividad."
            }
            println("aaa")
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
        */
    }

    fun find() {
/*
        if(_uiState.value.ClgCode.isEmpty()){
            _uiState.update { currentState -> currentState.copy(
                message = true,
                text = "Formato no válido"
            ) }
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            repository.getActividadById(_uiState.value.ClgCode.toInt()).collect { item ->
                if(item != null){
                    _uiState.update { currentState -> currentState.copy(
                        nota = item.nota,
                        ActivityDate = item.ActivityDate,
                        ActivityTime = item.ActivityTime,
                        CardCode= item.CardCode,
                        EndTime= item.EndTime,
                        Action= item.Action,
                        Priority = item.Priority,
                        Tel = item.Tel,
                        ClgCode = item.ClgCode,
                        U_SEIPEDIDOCLIENTE = item.U_SEIPEDIDOCLIENTE.toString(),
                        U_SEIPEDIDOCOMPRAS = item.U_SEIPEDIDOCOMPRAS.toString()
                    ) }
                }else{
                    _uiState.update { currentState -> currentState.copy(
                        message = true,
                        text = "Actividad no encontrada con número: ${_uiState.value.ClgCode}"
                    ) }
                }
            }
        }
        */
    }

    fun guardar(persistencia:Boolean) {
        /*
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
        val newActivity = Actividad(0,nota,ActivityDate,ActivityTime,CardCode,EndTime,Action,Tel,ClgCode,Priority,pedidoCompra,pedidoCliente)
        //val newActivity = Actividad(0,nota,ActivityDate,ActivityTime,CardCode,EndTime,Action)
        var text ="Nueva Actividad añadida"
        viewModelScope.launch {
            try{
                repository.addActividad(newActivity)
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
        */
    }

    fun menssageFunFalse() {
        _uiState.update { currentState -> currentState.copy(
            message = false
        ) }
    }
}