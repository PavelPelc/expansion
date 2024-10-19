package bi.ui.pane

import bi.ui.MenuItem
import bi.ui.Redrawable
import bi.ui.UiFsm
import bi.ui.UiTransition
import bi.ui.art.TextArt
import com.expansion.ui.UiContext
import com.expansion.ui.UiContext.Companion.colorBlue
import com.expansion.ui.UiContext.Companion.colorYellow
import javafx.event.Event
import javafx.event.EventHandler
import javafx.scene.Group
import javafx.scene.input.KeyEvent
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import kotlin.math.max

abstract class AbstractScreen : Pane(), Redrawable {
    var group = Group()
    private var menuGroup = Group()
    var menuRows = 0
    var keyHandlers = mutableListOf<EventHandler<KeyEvent>>()
    abstract fun menuItems() : List<MenuItem>

    protected fun makeMenu() {
        menuGroup.children.clear()

        keyHandlers.clear()
        val menuItems = menuItems()
        if (menuItems.isEmpty()) return
        var maxText = 0
        var maxKey = 0
        menuItems.forEach {
            maxText =max(maxText,it.text.length)
            maxKey = max(maxKey, it.key.length)
        }
        val max = maxText + maxKey + 4 + 1
        var columns =(UiContext.COLUMNS - 2) / max
        if (columns > menuItems.size) columns = menuItems.size

        menuRows = 2 + menuItems.size / columns
        if (menuItems.size % columns != 0) menuRows ++
        val border = (UiContext.ROWS - menuRows) * UiContext.TEXT_SIZE
        val offset = UiContext.EM * ((UiContext.COLUMNS - columns * max - 2).toDouble() / 2.0)

        val line = Rectangle()
        line.x = UiContext.EM / 2.0
        line.y = border + UiContext.TEXT_SIZE / 2.0
        line.width = UiContext.PANE_WIDTH - UiContext.EM
        line.height = (menuRows - 1) * UiContext.TEXT_SIZE
        line.arcWidth = UiContext.TEXT_SIZE
        line.arcHeight = UiContext.TEXT_SIZE
        line.fill = colorYellow
        line.stroke = colorBlue
        line.strokeWidth = 2.0
        menuGroup.children.add(line)

        var col = 0
        var row = 1 //one for border

        menuItems.forEach {
            val art = TextArt("${it.key} .. ${it.text}", offset+UiContext.EM*(col*max+maxKey-it.key.length), border + row*UiContext.TEXT_SIZE, Color.rgb(0, 87, 183))
            menuGroup.children.addAll(art.enlist())
            art.label.onMouseClicked = eventHandler(it.transition)
            keyHandlers.add(keyHandler(it.key, it.transition))
            col++
            col %= columns
            col.takeIf { it == 0 }?.let { row++ }
        }

        println("KeyHandles:${keyHandlers.size}")
    }

    init {
        println("AbsSrc init")
        children.add(group)
        children.add(menuGroup)
        makeMenu()
    }
    fun eventHandler(transition: UiTransition) = EventHandler { _: Event -> UiFsm.processTransition(transition)}
    protected fun keyHandler(keys:String, transition: UiTransition) = EventHandler { event : KeyEvent ->
        run {
            println("|${event.text}|")
            if (event.text.isNotEmpty() && keys.uppercase().indexOf(event.text.uppercase()) >= 0) {
                UiFsm.processTransition(transition)
            }
        }
    }
}