package bi.ui

interface Redrawable {

    fun needsRedraw() : Boolean

    fun redraw()

}