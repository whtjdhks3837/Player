package baejang.dynamic_media_view.ui.view

import baejang.dynamic_media_view.util.log

data class Area(val width: Int, val height: Int, var x: Float, var y: Float) {
    fun getXArea() = (x - (width / 2) - 30..x + (width / 2) + 30)
    fun print() = log("width : $width , height : $height , x : $x , y : $y")
}