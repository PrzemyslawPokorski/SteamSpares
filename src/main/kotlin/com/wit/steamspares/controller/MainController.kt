package com.wit.steamspares.controller

import com.google.gson.Gson
import com.wit.steamspares.app.Styles
import com.wit.steamspares.model.Game
import javafx.collections.FXCollections
import javafx.scene.control.ToggleButton
import tornadofx.Controller
import tornadofx.addClass
import tornadofx.removeClass
import java.io.FileWriter

class MainController : Controller() {
    var gson = Gson()

    var gamelist = mutableListOf<Game>(
            Game("Name 1", "Code 1", false, "A note"),
            Game("Name 2", "Code 2", false, "A note"),
            Game("Name 3", "Code 3", true),
            Game("Name 4", "Code 4", false)
    )

    fun loadFromJson(){
        //TODO: load games from json
    }

    fun saveToJson(){
        gson.toJson(gamelist, FileWriter("src/main/kotlin/com/wit/steamspares/data/games.json"))
    }

    fun addToList(name : String, code : String, status : Boolean, notes : String? = null){
        val newGame = Game(name, code, status, notes)
        println("Adding new game: $newGame")
        gamelist.add(newGame)
    }

    fun removeFromList(id : Int){
        var game = gamelist.find { it.id == id }
        if(game != null)
            gamelist.remove(game)
    }

    fun updateInList(id : Int, name : String, code : String, status : Boolean, notes : String? = null){
        var game = gamelist.find { it.id == id }
        println("Changing game: $game")
        if (game != null) {
            game.name = name
            game.code = code
            game.status = status
            game.notes = notes
        }
    }
}