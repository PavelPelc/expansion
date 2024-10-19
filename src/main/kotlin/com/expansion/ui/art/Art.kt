package bi.ui.art

import javafx.scene.Node

interface Art {

    fun draw()

    fun enlist() : List<Node>

    fun disable() = enlist().forEach{it.visibleProperty().set(false)}

    fun enable() = enlist().forEach{it.visibleProperty().set(true)}

}