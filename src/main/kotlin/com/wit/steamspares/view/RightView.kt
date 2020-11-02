package com.wit.steamspares.view

import com.wit.steamspares.model.Game
import tornadofx.*
import javax.swing.text.html.ListView

class RightView : View("My View") {
    override val root = hbox {
        tableview<Game> {
            fitToParentSize()
        }
    }
}
