package baejang.dynamic_media_view.ui

import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import baejang.dynamic_media_view.util.log

class GestureHelper(private val view: View) : GestureDetector.OnGestureListener {

    private val detector = GestureDetectorCompat(view.context, this)

    init {
        view.setOnTouchListener { _, motionEvent ->
            if (detector.onTouchEvent(motionEvent)) false else false
        }
    }

    override fun onShowPress(event: MotionEvent) {
        log("onShowPress")
    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        log("onSingleTapUp")
        return true
    }

    override fun onDown(event: MotionEvent): Boolean {
        log("onDown")
        return true
    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        log("onFling")
        return true
    }

    override fun onScroll(
        startEvent: MotionEvent,
        moveEvent: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        log("onScroll \n$startEvent \n$moveEvent \n x : $distanceX , y : $distanceY")
        return true
    }

    override fun onLongPress(p0: MotionEvent?) {
        log("onLongPress")
    }
}