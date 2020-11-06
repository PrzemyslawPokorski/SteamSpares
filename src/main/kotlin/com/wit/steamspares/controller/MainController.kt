package com.wit.steamspares.controller


import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.wit.steamspares.model.Game
import com.wit.steamspares.model.SteamAppModel
import tornadofx.Controller
import java.io.File
import java.io.IOException
import java.net.URL


class MainController : Controller() {
    var gson = GsonBuilder().setPrettyPrinting().create()
    var gamelist = loadFromJson()
    var steamList = downloadSteamAppList()

    fun downloadSteamAppList() : List<SteamAppModel>{
        var jsonString: String
        try {
            jsonString = URL("http://api.steampowered.com/ISteamApps/GetAppList/v0001/").readText()
            jsonString = jsonString.drop(26)
            jsonString = jsonString.dropLast(3)
            val type = object : TypeToken<MutableList<SteamAppModel>>() { }.type
            return gson.fromJson(jsonString, type)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
        return mutableListOf()
    }

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

    fun findSteamId(name : String) : Int{
        println("Steam apps count: ${steamList.size}")
        val app = steamList.find { it.name == name }
        if (app != null) {
            return app.appid
        }
        return 0
    }
}