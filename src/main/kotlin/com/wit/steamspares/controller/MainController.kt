package com.wit.steamspares.controller

import com.wit.steamspares.model.Game
import javafx.geometry.Pos
import javafx.scene.layout.HBox
import tornadofx.Controller

class MainController : Controller() {
    var games = ArrayList<Game>()

    fun loadFromJson(){
        //TODO: load games from json
    }

    fun saveToJson(){

    }
}