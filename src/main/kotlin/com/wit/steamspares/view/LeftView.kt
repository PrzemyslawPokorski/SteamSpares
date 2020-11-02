package com.wit.steamspares.view

import com.wit.steamspares.app.Styles
import javafx.geometry.Pos
import javafx.scene.paint.Color
import tornadofx.*

class LeftView : View("My View") {
    override val root = vbox {

        minWidth = 300.0
        fitToParentHeight()

        hbox{
            label("Name") {
                addClass(Styles.field_label)
            }
            textfield(){
                addClass(Styles.textfield)
            }
        }

        hbox{
            label("Code"){
                addClass(Styles.field_label)
            }
            textfield(){
                addClass(Styles.textfield)
            }
        }

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

        hbox{
            label("Notes"){
                addClass(Styles.field_label)
            }
            textfield(){
                addClass(Styles.textfield)
            }
        }

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
