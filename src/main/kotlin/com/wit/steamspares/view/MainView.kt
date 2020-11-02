package com.wit.steamspares.view

import com.wit.steamspares.app.Styles
import com.wit.steamspares.controller.MainController
import com.wit.steamspares.model.Game
import com.wit.steamspares.model.GameModel
import javafx.geometry.Pos
import javafx.scene.control.TextField
import javafx.scene.control.Toggle
import tornadofx.*

class MainView : View("Steam Spares") {
    val controller : MainController by inject()
    val model : GameModel by inject()

    var nameField : TextField by singleAssign()
    var codeField : TextField by singleAssign()
    var notesField : TextField by singleAssign()
    var usedButton : Toggle by singleAssign()
    var games = listOf(
            Game(1, "Name 1", "Code 1", false, "A note"),
            Game(2, "Name 2", "Code 2", false, "A note"),
            Game(3, "Name 3", "Code 3", true),
            Game(4, "Name 3", "Code 3", false)
    )

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

                        addClass(Styles.toggle)


                        text = if (isSelected) "Used" else "Unused"

                        style{
                            backgroundColor += if (isSelected) c("red") else c("green")
                        }

                        action{
                            text = if (text == "Unused") "Used" else "Unused"

                            style{
                                backgroundColor += if (isSelected) c("red") else c("green")
                            }
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
                    }
                    button("Delete"){
                        addClass(Styles.button)
                    }
                }
            }

            //RIGHT PANEL
            hbox {
                var (used, unused) = games.partition { it.status }

                tabpane(){
                    tab("Unused"){
                        tableview<Game>(unused.asObservable()) {
                            readonlyColumn("Name", Game::name)
                            readonlyColumn("Code", Game::code)
                            readonlyColumn("Notes", Game::notes)

                            fitToParentSize()
                        }
                    }
                    tab("Used"){
                        tableview<Game>(used.asObservable()) {
                            readonlyColumn("Name", Game::name)
                            readonlyColumn("Code", Game::code)
                            readonlyColumn("Notes", Game::notes)

                            fitToParentSize()
//                    column("Name", )

                        }
                    }
                }
            }
        }
    }

    init {
        setWindowMinSize(1200, 800)

    }
}
