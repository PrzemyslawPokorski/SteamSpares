package com.wit.steamspares.model

//Do something about unique id ?
data class Game(var appid : Int, var name: String = "Missing", var code : String, var status : Boolean, var notes : String? = null, var url : String? = "") {
    //Game code will always be unique so we can use that as internal storage id
    val id = code.hashCode()

    init{
        url = "https://store.steampowered.com/app/$appid"
        name = if (name.isEmpty()) "Name not given" else name
        code = if (code.isEmpty()) "Code not given" else code
    }
}