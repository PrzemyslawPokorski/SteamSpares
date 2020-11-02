package com.wit.steamspares.model

import tornadofx.ItemViewModel
import tornadofx.toProperty

class GameModel : ItemViewModel<Game>() {
    val name = bind {item.name.toProperty()}
    val code = bind {item.name.toProperty()}
    val used = bind {item.status.toProperty()}
    val notes = bind {item?.status.toProperty()}
}