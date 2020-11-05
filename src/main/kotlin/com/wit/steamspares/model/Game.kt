package com.wit.steamspares.model

//Do something about unique id ?
data class Game(var name: String = "Missing", var code : String, var status : Boolean, var notes : String? = null) {
    //Code will always be unique by definition (or -1 on offchance it was forced empty)
    val id = code.hashCode()

    init{
        name = if (name.isEmpty()) "Name not given" else name
        code = if (code.isEmpty()) "Code not given" else code
    }
}