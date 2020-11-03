package com.wit.steamspares.controller


import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.wit.steamspares.model.Game
import tornadofx.Controller
import java.io.File
import java.io.IOException


class MainController : Controller() {
    var gson = GsonBuilder().setPrettyPrinting().create()
//    var gamelist = loadFromJson()
    var gamelist = mutableMapOf(123456 to Game("Game 1", "Code 1", false))

    fun loadFromJson() : MutableMap<Int, Game>{
        val jsonString: String
        try {
            jsonString = File("src/main/kotlin/com/wit/steamspares/data/games.json").bufferedReader().use { it.readText() }
            val type = object : TypeToken<MutableList<Game>>() { }.type
            return gson.fromJson(jsonString, type)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
        return mutableMapOf()
    }

    fun saveToJson(){
        val jsonString = gson.toJson(gamelist)
        File("src/main/kotlin/com/wit/steamspares/data/games.json").writeText(jsonString)
    }

    fun addToList(name: String, code: String, status: Boolean, notes: String? = null){
        val newGame = Game(name, code, status, notes)
        gamelist[code.hashCode()] = newGame
    }

    fun removeFromList(id: Int){
        gamelist.remove(id)
    }

    fun updateInList(id: Int, name: String, code: String, status: Boolean, notes: String? = null){
        val game = Game(name, code, status, notes)
        gamelist[code.hashCode()] = game
    }
}