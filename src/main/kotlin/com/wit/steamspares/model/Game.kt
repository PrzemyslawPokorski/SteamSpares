package com.wit.steamspares.model

//Do something about unique id ?
data class Game(var name: String, var code : String, var status : Boolean, var notes : String? = null) {
    //Code will always be unique by definition
    val id = code.hashCode()
}