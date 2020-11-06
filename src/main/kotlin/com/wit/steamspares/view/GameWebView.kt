package com.wit.steamspares.view

import tornadofx.*

class GameWebView(url : String ? = "https://store.steampowered.com/" ) : View(""){

    override val root = webview() {
        setPrefSize(900.0, 600.0)
        titleProperty.bind(engine.titleProperty())

        engine.load(url)

    }
}