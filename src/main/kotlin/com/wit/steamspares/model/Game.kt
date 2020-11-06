package com.wit.steamspares.model

//Do something about unique id ?
data class Game(var id : Int, var name: String = "Missing", var code : String, var status : Boolean, var notes : String? = null) {
    init{
        var url = "https://store.steampowered.com/app/$id"
        name = if (name.isEmpty()) "Name not given" else name
        code = if (code.isEmpty()) "Code not given" else code

    }
}