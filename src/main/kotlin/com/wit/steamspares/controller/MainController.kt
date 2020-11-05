package com.wit.steamspares.controller


import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.wit.steamspares.model.Game
import tornadofx.Controller
import java.io.File
import java.io.IOException


class MainController : Controller() {
    var gson = GsonBuilder().setPrettyPrinting().create()
    var gamelist = loadFromJson()


    fun loadFromJson() : MutableList<Game>{
        val jsonString: String
        try {
            jsonString = File("src/main/kotlin/com/wit/steamspares/data/games.json").bufferedReader().use { it.readText() }
            val type = object : TypeToken<MutableList<Game>>() { }.type
            return gson.fromJson(jsonString, type)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
        return mutableListOf()
    }

    fun saveToJson(){
        val jsonString = gson.toJson(gamelist)
        File("src/main/kotlin/com/wit/steamspares/data/games.json").writeText(jsonString)
    }

    fun addToList(name: String, code: String, status: Boolean, notes: String? = null){
        val newGame = Game(name, code, status, notes)
        gamelist.add(newGame)
    }

    fun removeFromList(id: Int){
        val game = gamelist.find { it.id == id }
        if(game != null)
            gamelist.remove(game)
    }

    fun updateInList(id: Int, name: String, code: String, status: Boolean, notes: String? = null){
        val game = gamelist.find { it.id == id }
        if (game != null) {
            game.name = if(name.isEmpty()) "Name not given" else name
            game.code = if(code.isEmpty()) "Code not given" else code
            game.status = status
            game.notes = notes
        }
    }
}