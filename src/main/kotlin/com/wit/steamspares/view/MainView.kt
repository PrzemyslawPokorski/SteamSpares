package com.wit.steamspares.view

import com.wit.steamspares.app.Styles
import com.wit.steamspares.controller.MainController
import com.wit.steamspares.model.Game
import javafx.collections.FXCollections
//import com.wit.steamspares.model.GameModel
import javafx.geometry.Pos
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.control.ToggleButton
import tornadofx.*
import tornadofx.DefaultErrorHandler.Companion.filter

class MainView : View("Steam Spares") {
    val controller : MainController by inject()
//    val model : GameModel by inject()

    var nameField : TextField by singleAssign()
    var codeField : TextField by singleAssign()
    var notesField : TextField by singleAssign()
    var searchField : TextField by singleAssign()
    var usedButton : ToggleButton by singleAssign()
    var usedTable : TableView<Game> by singleAssign()
    var unusedTable : TableView<Game> by singleAssign()

    var games = mutableListOf(
            Game(1, "Name 1", "Code 1", false, "A note"),
            Game(2, "Name 2", "Code 2", false, "A note"),
            Game(3, "Name 3", "Code 3", true),
            Game(4, "Name 3", "Code 3", false)
    )

    val usedData = FXCollections.observableArrayList<Game>()
    val unusedData = FXCollections.observableArrayList<Game>()

    override val root = vbox {
        label(title) {
            addClass(Styles.heading)
            fitToParentWidth()
        }

        hbox {
            //LEFT PANEL
            vbox {
                minWidth = 300.0
                fitToParentHeight()

                //GAME NAME
                hbox{
                    label("Name") {
                        addClass(Styles.field_label)
                    }
                    textfield(){
                        nameField = this
                        addClass(Styles.textfield)
                    }
                }

                //GAME CODE
                hbox{
                    label("Code"){
                        addClass(Styles.field_label)
                    }
                    textfield(){
                        codeField = this
                        addClass(Styles.textfield)
                    }
                }

                //USED STATUS BUTTON
                hbox{
                    label("Status"){
                        addClass(Styles.field_label)
                    }
                    togglebutton("Unused"){
                        usedButton = this

                        addClass(Styles.toggle_unused)

                        selectedProperty().addListener{
                            obs, old, new ->
                            //Filter used and unused list by name contains?
                            setStatus(new, true)
                        }
                    }
                }

                //GAME NOTES
                hbox{
                    label("Notes"){
                        addClass(Styles.field_label)
                    }
                    textfield(){
                        notesField = this

                        addClass(Styles.textfield)
                    }
                }

                //SAVE/DELETE BUTTONS
                hbox{
                    alignment = Pos.CENTER
                    button("Save") {
                        addClass(Styles.button)

                        action {
                            println(unusedTable.selectedItem)
                        }
                    }

                    button("Delete"){
                        addClass(Styles.button)
                    }
                }
            }

            //RIGHT PANEL
            vbox {
                fitToParentSize()

                textfield {
                    fitToParentWidth()

                    textProperty().addListener{
                        obs, old, new ->
                        //Filter used and unused list by name contains?
                        println("Filter: " + new)
                        setTablesData(new)
                    }
                }

                tabpane(){
                    tab("Unused"){
                        tableview<Game>(unusedData) {
                            unusedTable = this

                            readonlyColumn("Name", Game::name)
                            readonlyColumn("Code", Game::code)
                            readonlyColumn("Notes", Game::notes)

                            onLeftClick {
                                if(selectedItem != null) {
                                    nameField.text = selectedItem?.name ?: "Name not given"
                                    codeField.text = selectedItem?.code ?: "Code missing"
                                    //If status is not true/false then data is corrupted (?)
                                    setStatus(selectedItem?.status!!)
                                    notesField.text = selectedItem?.notes
                                }
                                else
                                    clearSelections()
                            }

                            focusedProperty().addListener{
                                obs, old, new ->
                                if(!new)
                                    clearSelections()
                            }

                            fitToParentSize()
                        }
                    }
                    tab("Used"){
                        tableview<Game>(usedData) {
                            usedTable = this

                            readonlyColumn("Name", Game::name)
                            readonlyColumn("Code", Game::code)
                            readonlyColumn("Notes", Game::notes)

                            onLeftClick {
                                if(selectedItem != null) {
                                    nameField.text = selectedItem?.name ?: "Name not given"
                                    codeField.text = selectedItem?.code ?: "Code missing"
                                    //If status is not true/false then data is corrupted (?)
                                    setStatus(selectedItem?.status!!)
                                    notesField.text = selectedItem?.notes

                                    println(selectedItem)
                                }
                                else
                                    clearSelections()
                            }

                            focusedProperty().addListener{
                                obs, old, new ->
                                if(!new)
                                    clearSelections()
                            }

                            fitToParentSize()
                        }
                    }
                }
            }
        }
    }

    init {
        setWindowMinSize(1200, 800)

        setTablesData()
        clearSelections()
    }

    fun setTablesData(filter : String = ""){
        var filtered = games.filter {
            (it.name.contains(filter, ignoreCase = true)) ||
                    ((it.notes != null) && (it.notes.contains(filter)))
        }
        var (used, unused) = filtered.partition { it.status }

        unusedData.setAll(unused)
        usedData.setAll(used)
    }

    fun clearSelections(){
        usedTable.selectionModel.clearSelection()
        unusedTable.selectionModel.clearSelection()

        nameField.clear()
        codeField.clear()
        setStatus(false)
        notesField.clear()

    }

    fun setStatus(used : Boolean = false, styleOnly : Boolean = false){
        if (used){
            usedButton.text = "Used"
            usedButton.removeClass(Styles.toggle_unused)
            usedButton.addClass(Styles.toggle_used)
            if(!styleOnly)
                usedButton.isSelected = true
        }
        else{
            usedButton.text = "Unused"
            usedButton.removeClass(Styles.toggle_used)
            usedButton.addClass(Styles.toggle_unused)
            if(!styleOnly)
                usedButton.isSelected = false
        }
    }
}
