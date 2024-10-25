package bi.ui.pane

import bi.ui.MenuItem
import bi.ui.UiTransition
import bi.ui.art.TextArt
import com.expansion.ui.UiContext
import com.expansion.ui.art.MiniTextArt
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import java.util.*
import kotlin.math.abs

class SplashScreen : AbstractScreen() {

    override fun menuItems(): List<MenuItem> = listOf()


    private val rnd = Random(System.currentTimeMillis())
    private val appName = "Auto Expansion Mania"
    private val chars = " $appName".toCharArray()
    private val info = MiniTextArt("",20.0, UiContext.PANE_HEIGHT-20.0, Color.BLACK)

    init {
        println("SplashSrc init")

        background = Background(BackgroundFill(Color.LIGHTGRAY,null,null))

        val r = Rectangle(0.0, 0.0, UiContext.PANE_WIDTH, UiContext.PANE_HEIGHT)
        r.fill = Color.LIGHTGRAY
        r.stroke = Color.RED
        r.strokeWidth = 1.0
        val g = Rectangle(1.0, 1.0, UiContext.PANE_WIDTH-1.0, UiContext.PANE_HEIGHT-1.0)
        g.fill = Color.LIGHTGRAY
        g.stroke = Color.GREEN
        g.strokeWidth = 1.0

        val b = Rectangle(2.0, 2.0, UiContext.PANE_WIDTH-2.0, UiContext.PANE_HEIGHT-2.0)
        b.fill = Color.LIGHTGRAY
        b.stroke = Color.BLUE
        b.strokeWidth = 1.0

        group.children.addAll(listOf(r,g,b))


        val middle = UiContext.ROWS / 2
        val third = UiContext.PANE_WIDTH / 3
        for (row in 0 until UiContext.ROWS) {
            when  {
                row == middle -> {
                    var word = word((third / UiContext.EM).toInt())
                    var art = TextArt(word, 0.0, UiContext.TEXT_SIZE * row)
                    group.children.addAll(art.enlist())
                    word = word((third / UiContext.EM).toInt())
                    art = TextArt(word, 2.0*third, UiContext.TEXT_SIZE * row)
                    group.children.addAll(art.enlist())
                    art = TextArt(appName, third + ((third - appName.length* UiContext.EM)/2.0), UiContext.TEXT_SIZE * row, Color.BLACK)
                    group.children.addAll(art.enlist())

                }
                row ==  middle - 1 || row == middle + 1 -> {
                    var word = word((third / UiContext.EM).toInt())
                    var art = TextArt(word, 0.0, UiContext.TEXT_SIZE * row)
                    group.children.addAll(art.enlist())
                    word = word((third / UiContext.EM).toInt())
                    art = TextArt(word, 2.0*third, UiContext.TEXT_SIZE * row)
                    group.children.addAll(art.enlist())

                }
                else -> {
                    var position = 0.0
                    while (position < UiContext.PANE_WIDTH) {
                        val len = 3 + rnd.nextInt(11)
                        val word = word(len)
                        val art = TextArt(word, position, UiContext.TEXT_SIZE * row)
                        art.draw()
                        group.children.addAll(art.enlist())

                        position += art.label.boundsInLocal.width  + UiContext.TEXT_SIZE
                    }
                }
            }

        }

        group.children.addAll(info.enlist())


        Thread(Runnable {
            load();
            onMouseClicked = eventHandler(UiTransition.END_INIT)
            keyHandlers.add(keyHandler(" ",UiTransition.END_INIT))

        }).start()

    }

    private fun word(len: Int): String {
        val sb= StringBuilder()

        for (i in 0 until len) {
            sb.append(chars[abs(rnd.nextInt()) % chars.size])
        }

        return sb.toString()
    }

    var needsRedraw = true
    override fun needsRedraw(): Boolean {
        return needsRedraw
    }

    override fun redraw() {
    }

    fun load() {

    }


}


