package com.AG_AP.electroshop.viewModels

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.ViewModel
import com.AG_AP.electroshop.R
import com.AG_AP.electroshop.uiState.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import android.widget.ImageView
import android.webkit.WebView
import android.view.View
import at.favre.lib.crypto.bcrypt.BCrypt
import com.AG_AP.electroshop.MainActivity
import com.AG_AP.electroshop.firebase.SEIConfigCRUD
import com.AG_AP.electroshop.functions.SessionObj


import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {


    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()


    fun usernameChange(user: String) {
        _uiState.update { currentState ->
            currentState.copy(
                username = user
            )
        }
    }

    fun passwordChange(pass: String) {
        _uiState.update { currentState ->
            currentState.copy(
                password = pass
            )
        }
    }

    fun resetData() {
        _uiState.update {
            LoginUiState(
                password = "",
                username = "",
                message = false
            )
        }
    }

    fun saveConnection() {
        val userName = _uiState.value.username
        val pass = _uiState.value.password
        if (userName.isNotEmpty() && pass.isNotEmpty()) {
            //val hashedPass = encryptPass(pass)
            //TODO sacar la contraseña de la base de datos y compararla con la que se pasa
            if (validateUsername(userName) /*&& validatePass(pass, hashedPass)*/) {
                SEIConfigCRUD.getSEIConfigById(userName){ data ->
                    if(data != null){
                        if(data.U_password == pass){
                            SessionObj.inserData(
                                data.U_name,
                                data.U_articulo,
                                data.U_actividad,
                                data.U_PedidoCI,
                                data.U_PedidoCO
                            )
                            _uiState.update { currentState -> currentState.copy(
                                paso = true,
                            ) }
                        }else{
                            _uiState.update { currentState -> currentState.copy(
                                message = true,
                                text = "Usuario o contraseña incorrecta."
                            ) }
                        }
                    }else{
                        _uiState.update { currentState -> currentState.copy(
                            message = true,
                            text = "Usuario o contraseña incorrecta."
                        ) }
                    }
                }

            }
        }else{
            _uiState.update { currentState -> currentState.copy(
                text = "Usuario o contraseña vacía.",
                message = true
            ) }
        }
    }

    fun menssageFunFalse(){
        _uiState.update { currentState -> currentState.copy(
            message = false
        ) }
    }


    /**
     * Method that validates username String with some different illegal characters.
     * @return false when the username String contains any of the illegal characters, otherwise
     * a true is thrown when it doesn't contains any.
     */
    private fun validateUsername(username: String): Boolean {
        var notValidCharacters: String = "-/*ñÑ|&/"

        notValidCharacters.forEach {
            if (username.contains(it)) {
                return false
            }
        }
        return true
    }

    /**
     * Method that encrypts the pass with a complexity of 12
     * @return a hashed String
     */
    private fun encryptPass(pass: String): String {
        return BCrypt.withDefaults().hashToString(12, pass.toCharArray())
    }

    /**
     * Method that validates the integrity of the pass, checking whether or not is valid
     * @return true if the pass is valid, otherwise a false is thrown
     */
    private fun validatePass(pass: String, hash: String): Boolean {
        return BCrypt.verifyer().verify(pass.toCharArray(), hash).verified
    }




}