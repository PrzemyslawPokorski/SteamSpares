package com.wit.steamspares.view

import tornadofx.*

class GameWebView(url : String ? = "https://store.steampowered.com/" ) : View(""){

    override val root = webview() {
        titleProperty.bind(engine.titleProperty())

        engine.load(url)

    }
}