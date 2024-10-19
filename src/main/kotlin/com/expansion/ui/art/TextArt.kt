package bi.ui.art


import com.expansion.ui.UiContext
import javafx.scene.Node
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text

class TextArt(var text:String, var x : Double, var y: Double, var color: Color = Color.DARKGREY) : Art {

    val label  = Text()

    init {
        label.text = text
        label.x = x
        label.y = y + UiContext.TEXT_SIZE - UiContext.TEXT_HEIGHT_OFFSET
        label.font = Font.font("Monospace", FontWeight.EXTRA_BOLD, UiContext.TEXT_SIZE)
        label.fill = color
    }
    override fun draw() {
    }

    override fun enlist() : List<Node> = listOf(label)

    fun resetText(text:String) {
        this.text = text
        label.text = text
    }
}