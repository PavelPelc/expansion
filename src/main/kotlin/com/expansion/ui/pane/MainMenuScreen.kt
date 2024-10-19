package com.expansion.ui.pane

import bi.ui.MenuItem
import bi.ui.UiTransition
import bi.ui.pane.AbstractScreen

class MainMenuScreen() : AbstractScreen() {

    private var needsRedraw = false

    override fun menuItems(): List<MenuItem> = listOf(
        MenuItem("Start Simulation", "SN", UiTransition.NEW_GAME),
        MenuItem("Blue Screen", "B", UiTransition.TO_GRAPH),
        MenuItem("Exit", "K", UiTransition.EXIT)
    )
    override fun needsRedraw(): Boolean = needsRedraw
    override fun redraw() {

    }


}
