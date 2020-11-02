package com.wit.steamspares.view

import com.wit.steamspares.model.Game
import tornadofx.*
import javax.swing.text.html.ListView

class RightView : View("My View") {
    override val root = hbox {
        val games = listOf(
                Game("Name 1", "Code 1", false, "A note"),
                Game("Name 2", "Code 2", false, "A note"),
                Game("Name 3", "Code 3", true),
                Game("Name 3", "Code 3", false)
        )

        tabpane(){
            tab("Unused"){
                tableview<Game>(games.asObservable()) {
//                    column("Name", Game::name)

                    fitToParentSize()
                }
            }
            tab("Used"){
                tableview<Game> {
                    fitToParentSize()
//                    column("Name", )

                }
            }
        }
    }
}
