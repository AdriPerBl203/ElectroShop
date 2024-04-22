package com.AG_AP.electroshop.funtions

import java.net.URL

fun validarURL(url: String): Boolean {
    return try {
        URL(url)
        true
    } catch (e: Exception) {
        false
    }
}