package com.wit.steamspares.model

//Do something about unique id ?
data class Game(val id : Int, val name: String, val code : String, val status : Boolean, val notes : String? = null) {

}