package com.AG_AP.electroshop.functions

object SessionObj {

    var name:String =""
    var password:String =""
    var articulo:String =""
    var actividad:String =""
    var pedidoCL:String =""
    var pedidoCO:String =""

    fun reset(){
        name =""
        password =""
        articulo =""
        actividad =""
        pedidoCL =""
        pedidoCO =""
    }

    fun checkLogin():Boolean{
        return this.name==""
    }

    fun inserData(name:String,articulo:String,actividad:String,pedidoCL:String,pedidoCO:String){
        this.name=name
        this.articulo=articulo
        this.actividad=actividad
        this.pedidoCL=pedidoCL
        this.pedidoCO=pedidoCO
    }

    override fun toString(): String {
        return "SessionObj(name='$name', password='$password', articulo='$articulo', actividad='$actividad', pedidoCL='$pedidoCL', pedidoCO='$pedidoCO')"
    }


}