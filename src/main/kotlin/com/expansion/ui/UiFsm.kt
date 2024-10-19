package bi.ui

import kotlin.system.exitProcess

class UiFsm {
    companion object {
        var state = UiState.START

        fun processTransition(transition: UiTransition) {
            println("UiFsm: $state -> $transition")
            if (transition==UiTransition.EXIT) exitProcess(0);
            if (transition==UiTransition.NONE) return
            state = when (state) {
                UiState.START -> when (transition) {
                    UiTransition.END_INIT -> UiState.MAIN_MENU
                    else -> UiState.BLUE_SCREEN
                }
                else -> UiState.BLUE_SCREEN
            }

        }
    }

}

enum class UiState() {


    START,// START FSM STATE
    END,// END FSM STATE
    BLUE_SCREEN,// error

    MAIN_MENU,
    GENERATE,
    GAME,
    GRAPH,
    GENERATE_PLACE,
    GENERATE_SEASON,
    TEAM_DETAIL_GENERATE_SEASON,

}

enum class UiTransition() {
    END_INIT,
    NEW_GAME,
    NEXT,
    SIMULATE,
    EXIT,
    TO_GRAPH,
    NONE,
    BACK,
    TO_TEAM_DETAIL,
}