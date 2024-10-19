package com.expansion.ui

import bi.ui.PaneManager
import bi.ui.art.TextArt
import javafx.scene.image.Image
import javafx.scene.paint.Color

class UiContext {

    companion object {
        val ROWS = 51
        val COLUMNS = 121
        val TEXT_SIZE = 16.0
        val TEXT_HEIGHT_OFFSET = 3.0

        val EM = TextArt("M",0.0,0.0).label.boundsInLocal.width

        val WINDOW_OFFSET = 15.0
        val PANE_WIDTH = COLUMNS * EM
        val WINDOW_WIDTH = PANE_WIDTH
        val PANE_HEIGHT = TEXT_SIZE * ROWS
        val WINDOW_HEIGHT = PANE_HEIGHT
        val REFRESH_MS = 25.0


        val colorBlue = Color.rgb(0, 87, 183)
        val colorYellow = Color.rgb(255, 215, 0)

        val paneManager = PaneManager()


    }
}



