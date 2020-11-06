package com.wit.steamspares.view

import com.wit.steamspares.app.Styles
import com.wit.steamspares.controller.MainController
import com.wit.steamspares.model.Game
import javafx.collections.FXCollections
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.*
import tornadofx.*
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection
import javax.swing.Action
import kotlin.properties.ObservableProperty

class MainView : View("Steam Spares") {
    val controller : MainController by inject()

    var nameField : TextField by singleAssign()
    var codeField : TextField by singleAssign()
    var notesField : TextField by singleAssign()
    var searchField : TextField by singleAssign()
    var usedButton : ToggleButton by singleAssign()
    var usedTable : TableView<Game> by singleAssign()
    var unusedTable : TableView<Game> by singleAssign()
    var topText : Label by singleAssign()

    val usedData = FXCollections.observableArrayList<Game>()
    val unusedData = FXCollections.observableArrayList<Game>()

    var selectedGame : Game? = null

    override val root = vbox {
        setMinSize(200.0, 200.0)
        label(title) {
            topText = this
            addClass(Styles.heading)
            fitToParentWidth()
        }

        vbox {
            fitToParentSize()
            hbox(30) {
                fitToParentSize()

                //LEFT PANEL
                vbox {
                    alignment = Pos.CENTER
                    minWidth = 300.0
                    fitToParentHeight()

                    //GAME NAME
                    hbox {
                        label("Name") {
                            addClass(Styles.field_label)
                        }
                        textfield() {
                            nameField = this
                            addClass(Styles.textfield)
                        }
                    }

                    //GAME CODE
                    hbox {
                        label("Code") {
                            addClass(Styles.field_label)
                        }
                        textfield() {
                            codeField = this
                            addClass(Styles.textfield)
                        }
                    }

                    //USED STATUS BUTTON
                    hbox {
                        label("Status") {
                            addClass(Styles.field_label)
                        }
                        togglebutton("Unused") {
                            usedButton = this

                            addClass(Styles.toggle_unused)

                            selectedProperty().addListener { obs, old, new ->
                                //Filter used and unused list by name contains?
                                setStatus(new, true)
                            }
                        }
                    }

                    //GAME NOTES
                    hbox {
                        label("Notes") {
                            addClass(Styles.field_label)
                        }
                        textfield() {
                            notesField = this

                            addClass(Styles.textfield)
                        }
                    }

                    //SAVE/DELETE BUTTONS
                    hbox(20) {
                        alignment = Pos.CENTER
                        button("Save") {
                            addClass(Styles.button)

                            val keycodePattern = """^[\w\d]{5}(-[\w\d]{5}){2}((-[\w\d]{5}){2})?${'$'}""".toRegex()

                            action {

                                val name = nameField.text.trim()
                                val code = codeField.text.trim()
                                val used = usedButton.isSelected
                                val note = notesField.text.trim()

                                if (name == null || code == null || name.isEmpty() || code.isEmpty()) {
                                    alert(Alert.AlertType.WARNING, "Empty fields not allowed",
                                        "Name and Code fields can not be empty!")
                                }
                                else if(!(code.matches(keycodePattern))){
                                    alert(Alert.AlertType.WARNING, "Unknown code pattern",
                                        "Your code doesn't match any steam code patterns")
                                }

                                else{
                                    //If adding new game
                                    if (selectedGame == null)
                                            controller.addToList(name, code, used, note)

                                    //If changing existing
                                    else {
                                        val id = selectedGame!!.id

                                        if (name != null && code != null && name != "" && code != "")
                                            controller.updateInList(id, name, code, used, note)
                                    }

                                    setTablesData()
                                    clearSelections()
                                    controller.saveToJson()
                                }
                            }
                        }

                        button("Delete") {
                            addClass(Styles.button)

                            action {
                                if (selectedGame != null) {
                                    controller.removeFromList(selectedGame?.id!!)

                                    setTablesData()
                                    clearSelections()
                                    controller.saveToJson()
                                }
                            }
                        }
                    }
                }

                separator {
                    orientation = Orientation.VERTICAL
                }

                //RIGHT PANEL
                vbox(10) {
                    fitToParentSize()
                    alignment = Pos.CENTER

                    textfield {
                        fitToParentWidth()

                        promptText = "Filter by ..."

                        textProperty().addListener { obs, old, new ->
                            //Filter used and unused list by name contains?
                            setTablesData(new.trim())
                        }
                    }

                    tabpane() {
                        tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
                        fitToParentSize()

                        tab("Unused") {
                            tableview<Game>(unusedData) {
                                unusedTable = this
                                fitToParentSize()

                                readonlyColumn("Name", Game::name) {
                                    prefWidth = 250.0
                                }
                                readonlyColumn("Code", Game::code) {
                                    prefWidth = 150.0
                                }
                                readonlyColumn("Notes", Game::notes){
                                    prefWidth = 300.0
                                }

                                readonlyColumn("Link", Game::url){
                                    cellFormat {
                                        link ->
                                        graphic = hyperlink("Steam page") {
                                            setOnAction {
                                                val gameView = GameWebView(link)

                                                val webWindow = gameView.openWindow()
                                                webWindow?.minWidth = 1200.0
                                                webWindow?.minHeight = 900.0
                                            }
                                        }
                                    }
                                }

                                onLeftClick {
                                    if (selectedItem != null && selectedItem != selectedGame) {
                                        nameField.text = selectedItem?.name ?: "Name not given"
                                        codeField.text = selectedItem?.code ?: "Code missing"
                                        //If status is not true/false then data is corrupted (?)
                                        setStatus(selectedItem?.status!!)
                                        notesField.text = selectedItem?.notes

                                        selectedGame = selectedItem
                                    } else
                                        clearSelections()
                                }

                                onUserSelect {
                                    val c: Clipboard = Toolkit.getDefaultToolkit().getSystemClipboard()
                                    val clip = selectedItem?.name + ": " + selectedItem?.code + " (" + selectedItem?.url + ")"
                                    c.setContents(StringSelection(clip), StringSelection(clip))
                                    topText.text = "Code Copied !"
                                    //TODO: Find a way to display tooltip and hide after a while on double click
//                                tooltip("Code copied to clipboard!"){
//                                    onShown = EventHandler {
//                                        println("Shown")
//                                        this.text = ""
//                                    }
//                                }
                                }
                            }
                            onLeftClick { clearSelections() }
                        }
                        tab("Used") {
                            tableview<Game>(usedData) {
                                usedTable = this

                                readonlyColumn("Name", Game::name) {
                                    prefWidth = 250.0
                                }
                                readonlyColumn("Code", Game::code) {
                                    prefWidth = 150.0
                                }
                                readonlyColumn("Notes", Game::notes){
                                    prefWidth = 300.0
                                }

                                readonlyColumn("Link", Game::url){
                                    cellFormat {
                                            link ->
                                        graphic = hyperlink("Steam page") {
                                            setOnAction {
                                                val gameView = GameWebView(link)

                                                val webWindow = gameView.openWindow()
                                                webWindow?.minWidth = 1200.0
                                                webWindow?.minHeight = 900.0
                                            }
                                        }
                                    }
                                }

                                onLeftClick {
                                    if (selectedItem != null && selectedItem != selectedGame) {
                                        nameField.text = selectedItem?.name ?: "Name not given"
                                        codeField.text = selectedItem?.code ?: "Code missing"
                                        //If status is not true/false then data is corrupted (?)
                                        setStatus(selectedItem?.status!!)
                                        notesField.text = selectedItem?.notes

                                        selectedGame = selectedItem
                                    } else
                                        clearSelections()
                                }

                                fitToParentSize()
                            }
                            onLeftClick { clearSelections() }
                        }
                    }
                }

                //Styling only vbox - no idea how to do margins
                vbox {}
            }

            //Styling only vbox - no idea how to do margins
            separator { minHeight = 20.0 }
            vbox{}
        }
    }

    init {
        setWindowMinSize(1200, 800)

        setTablesData()
        clearSelections()
    }

    fun setTablesData(filter: String = ""){
        var filtered = controller.gamelist.filter {
            (it.name.contains(filter, ignoreCase = true)) ||
                    ((it.notes != null) && (it.notes!!.contains(filter, ignoreCase = true)))
        }
        filtered = filtered.sortedBy { it.name }
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

        selectedGame = null
        topText.text = title
    }

    fun setStatus(used: Boolean = false, styleOnly: Boolean = false){
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
