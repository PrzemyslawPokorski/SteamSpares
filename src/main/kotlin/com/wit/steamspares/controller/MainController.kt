package com.wit.steamspares.controller

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.wit.steamspares.model.Game
import tornadofx.Controller
import java.io.File


class MainController : Controller() {
    var gson = GsonBuilder().setPrettyPrinting().create()

    var gamelist = mutableListOf<Game>(
            Game("Name 1", "Code 1", false, "A note"),
            Game("Name 2", "Code 2", false, "A note"),
            Game("Name 3", "Code 3", true),
            Game("Name 4", "Code 4", false)
    )

    fun loadFromJson(){
        //TODO: load games from json
        val file = File("src/main/kotlin/com/wit/steamspares/data/games.json")

        if(file.exists()){
            gson.fromJson(file.path, Game::class.java)
        }

    }

    fun saveToJson(){
        val jsonString = gson.toJson(gamelist)
        File("src/main/kotlin/com/wit/steamspares/data/games.json").writeText(jsonString)
    }

    fun addToList(name: String, code: String, status: Boolean, notes: String? = null){
        val newGame = Game(name, code, status, notes)
        println("Adding new game: $newGame")
        gamelist.add(newGame)
    }

    fun removeFromList(id: Int){
        var game = gamelist.find { it.id == id }
        if(game != null)
            gamelist.remove(game)
    }

    fun updateInList(id: Int, name: String, code: String, status: Boolean, notes: String? = null){
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