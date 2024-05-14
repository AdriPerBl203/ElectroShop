package com.AG_AP.electroshop.viewModels

interface ActionViewModel {
    fun save(data: Boolean)
    fun update()
    fun delete()

    fun find()

    fun menssageFunFalse()
}