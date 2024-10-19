package com.expansion.ui.art

import bi.ui.art.Art
import com.expansion.ui.UiContext
import javafx.scene.Node
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text

class MiniTextArt(var text:String, var x : Double, var y: Double, var color: Color = Color.DARKGREY) : Art {

    val label  = Text()

    init {
        label.text = text
        label.x = x
        label.y = y + UiContext.TEXT_SIZE/2 - UiContext.TEXT_HEIGHT_OFFSET
        label.font = Font.font("Monospace", FontWeight.EXTRA_BOLD, UiContext.TEXT_SIZE/2)
        label.fill = color
    }
    override fun draw() {
    }


    fun resetText(text:String) {
        this.text = text
        label.text = text
    }


    override fun enlist() : List<Node> = listOf(label)
}