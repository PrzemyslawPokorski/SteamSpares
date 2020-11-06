package com.wit.steamspares.view

import com.wit.steamspares.app.Styles
import javafx.scene.Scene
import javafx.scene.web.PopupFeatures
import javafx.scene.web.WebEngine
import javafx.stage.Stage
import javafx.stage.StageStyle
import javafx.util.Callback
import tornadofx.*

class GameWebView : View(""){

    override val root = webview() {
        setMinSize(200.0, 200.0)
        titleProperty.bind(engine.titleProperty())

        engine.load("https://store.steampowered.com/")

    }
}