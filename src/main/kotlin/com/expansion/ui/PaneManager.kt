package bi.ui

import bi.ui.pane.AbstractScreen
import bi.ui.pane.BlueScreen
import bi.ui.pane.SplashScreen

class PaneManager() {

    val paneMap = mutableMapOf<UiState, AbstractScreen>()

    init {
        paneMap[UiState.START] = SplashScreen()
        paneMap[UiState.BLUE_SCREEN] = BlueScreen()

    }

    fun load() {

       // paneMap[UiState.MAIN_MENU] = MainMenuScreen()

    }

    fun currentScreen() = paneMap[UiFsm.state]

    fun screen(state: UiState) = paneMap[state]

    fun first() = paneMap[UiState.BLUE_SCREEN]!!

}