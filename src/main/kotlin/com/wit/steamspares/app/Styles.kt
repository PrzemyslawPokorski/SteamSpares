package com.wit.steamspares.app

import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val heading by cssclass()
        val field_label by cssclass()
        val textfield by cssclass()
        val table by cssclass()
        val toggle by cssclass()
        val button by cssclass()
    }

    init {
        label and heading {
            padding = box(20.px)
            fontSize = 30.px
            fontWeight = FontWeight.BOLD
            alignment = Pos.CENTER
        }

        field_label{
            padding = box(10.px, 20.px)
            fontSize = 14.px
            fontWeight = FontWeight.BOLD
            minWidth = 100.px
            alignment = Pos.CENTER_LEFT
        }

        textfield {
            fontSize = 14.px
            fontWeight = FontWeight.BOLD
            minWidth = 200.px
        }

        toggle{
            minHeight = 30.px
            minWidth = 200.px
            fontWeight = FontWeight.BOLD
            alignment = Pos.CENTER
            textFill = Color.WHITE
        }

        button{
            padding = box(20.px)
            alignment = Pos.CENTER
            fontWeight = FontWeight.BOLD
            minWidth = 100.px
        }
    }
}