package com.wit.steamspares.controller

import com.wit.steamspares.app.Styles
import com.wit.steamspares.model.Game
import javafx.collections.FXCollections
import javafx.scene.control.ToggleButton
import tornadofx.Controller
import tornadofx.addClass
import tornadofx.removeClass

class MainController : Controller() {
    var games = ArrayList<Game>()

    var gamelist = listOf<Game>()

    fun loadFromJson(){
        //TODO: load games from json
    }

    fun saveToJson(){

    }

    fun addToList(name : String, code : String, status : Boolean, notes : String? = null){

    }

    fun removeFromList(id : Int){

    }

    fun updateInList(id : Int, name : String, code : String, status : Boolean, notes : String? = null){

    }
}