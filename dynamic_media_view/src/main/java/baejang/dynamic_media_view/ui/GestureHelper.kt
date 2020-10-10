package baejang.dynamic_media_view.ui

import android.view.MotionEvent
import android.view.View
import baejang.dynamic_media_view.util.log
import kotlin.math.abs

class GestureHelper(
    private val parentView: View,
    private val playerView: View,
    private val bottomView: View
) {

    private enum class Action {
        UpScroll, DownScroll, Idle
    }

    private var action = Action.Idle

    init {
        parentView.setOnTouchListener { _, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_MOVE -> onMove(event)
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> onCancel(event)
            }
            true
        }
    }

    private fun onMove(event: MotionEvent) {
        if (event.historySize == 0) return
        val currentY = event.getY(0)
        if (currentY <= 0) return
        val offset = currentY - event.getHistoricalY(0, 0)
        log("offset : $offset")
        if (offset in -10f..10f) return
        if (offset < 0) onUpGesture(offset)
        else if (offset > 0) onDownGesture(offset)
    }

    private fun onUpGesture(offset: Float) {
        action = Action.UpScroll
        playerView.pivotY = 0f
        playerView.pivotX = parentView.width / 2f
        val per = abs(offset) / parentView.height
        playerView.scaleY -= per
        playerView.scaleX -= per
    }

    private fun onDownGesture(offset: Float) {
        action = Action.DownScroll
        if (playerView.scaleY >= 1f) return
        playerView.pivotY = 0f
        playerView.pivotX = parentView.width / 2f
        val per = abs(offset) / parentView.height
        playerView.scaleY = if (playerView.scaleY + per >= 1) 1f else playerView.scaleY + per
        playerView.scaleX += per
    }

    private fun onCancel(event: MotionEvent) {
        action = Action.Idle
        playerView.scaleY = 1f
        playerView.scaleX = 1f
    }
}