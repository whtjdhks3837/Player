package baejang.dynamic_media_view.ui.view

import android.view.MotionEvent
import baejang.dynamic_media_view.util.log

data class Area(val width: Int, val height: Int, var x: Float, var y: Float) {
    fun getXArea() = (x - (width / 2) - 30..x + (width / 2) + 30)
    fun getYArea() = (y - (height / 2) - 30..x + (height / 2) + 30)
    fun print() = log("width : $width , height : $height , x : $x , y : $y")
    companion object {
        fun isTouched(area: Area?, event: MotionEvent): Boolean {
            if (area == null) return false
            return event.x in area.getXArea() && event.y in area.getYArea()
        }
    }
}
