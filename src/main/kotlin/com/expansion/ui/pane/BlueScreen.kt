package bi.ui.pane

import bi.ui.*
import bi.ui.art.Art
import bi.ui.art.TextArt
import javafx.scene.Node
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import java.util.*
import kotlin.math.abs

class BlueScreen() : AbstractScreen() {


    init {
        println("BluSrc init")

        background = Background(BackgroundFill(Color.BLUE,null,null))
        val art = TextArt(" \n Expansion turns to blue.", 60.0, 60.0 )
        group.children.addAll(art.enlist())

    }

    override fun menuItems(): List<MenuItem> = listOf()

    override fun needsRedraw(): Boolean {
        return false
    }

    override fun redraw() {

    }
}


