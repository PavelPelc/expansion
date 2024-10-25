package com.expansion.ui

import bi.ui.UiFsm
import bi.ui.UiTransition
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.util.Duration


class BiMaApp : Application() {

    private val paneManager = UiContext.paneManager
    override fun start(stage: Stage) {

        val scene = Scene(paneManager.first(), UiContext.WINDOW_WIDTH, UiContext.WINDOW_HEIGHT, Color.TRANSPARENT)
        stage.scene = scene
        stage.title = "Bi Ma  - 0.0.1"
        paneManager.first().visibleProperty().set(true) //Maybe help on Ubuntu, on win works correctly without
        stage.isResizable=false
        stage.show()
        val loop = Timeline(
            KeyFrame(Duration.millis(UiContext.REFRESH_MS),{ updatePane(stage) })
        )
        loop.cycleCount = Timeline.INDEFINITE
        loop.play()
    }

    private fun updatePane(stage: Stage) {
        val pane = paneManager.currentScreen() ?: throw IllegalStateException("Screen not found")

        pane.takeIf { it.needsRedraw() }?.run {
            redraw()
        }

        if (pane != stage.scene.root) {
            val old = stage.scene.root
            //println("Changing PANE.")
            stage.scene.root = pane
            pane.visibleProperty().set(true)
            old.visibleProperty().set(false)
            stage.scene.onKeyPressed= EventHandler { keyEvent ->
                //println("KeyEvent: '${keyEvent.text}' -> ${pane.keyHandlers.size} handlers")
                pane.keyHandlers.forEach{
                it.handle(keyEvent)
            }}
        }
    }

    override fun stop() {
        UiFsm.processTransition(UiTransition.EXIT)
        super.stop()
    }
}

fun main() {
    Application.launch(BiMaApp::class.java)
}

