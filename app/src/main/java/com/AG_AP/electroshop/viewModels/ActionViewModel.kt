package com.AG_AP.electroshop.viewModels

interface ActionViewModel {
    fun guardar(data: Boolean)
    fun update()
    fun borrar()

    fun find()

    fun menssageFunFalse()
}