package com.wit.steamspares.view

import com.wit.steamspares.app.Styles
import tornadofx.*

class MainView : View("Steam Spares") {
    override val root = vbox {
        label(title) {
            addClass(Styles.heading)
        }
    }

    init {
        setWindowMinSize(1200, 800)
    }
}
