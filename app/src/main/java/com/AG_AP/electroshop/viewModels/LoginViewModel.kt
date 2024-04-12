package com.AG_AP.electroshop.viewModels

import androidx.lifecycle.ViewModel
import com.AG_AP.electroshop.R
import com.AG_AP.electroshop.uiState.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import android.widget.ImageView
import android.webkit.WebView
import android.view.View


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
        _uiState.update {
            currentState ->
            currentState.copy(
                password = pass
            )
        }
    }



}