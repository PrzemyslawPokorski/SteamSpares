package com.wit.steamspares.view

import com.wit.steamspares.app.Styles
import javafx.geometry.Pos
import javafx.scene.control.TextField
import javafx.scene.control.Toggle
import javafx.scene.paint.Color
import tornadofx.*

class LeftView : View("My View") {
    override val root = vbox {

        var nameField : TextField by singleAssign()
        var codeField : TextField by singleAssign()
        var notesField : TextField by singleAssign()
        var usedButton : Toggle by singleAssign()

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
                addClass(Styles.textfield)
            }
        }

        //USED STATUS BUTTON
        hbox{
            label("Status"){
                addClass(Styles.field_label)
            }
            togglebutton("Unused"){
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
}
