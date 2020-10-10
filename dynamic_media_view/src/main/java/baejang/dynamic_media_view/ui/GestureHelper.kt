package baejang.dynamic_media_view.ui

import android.view.MotionEvent
import android.view.View
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
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> onCancel()
            }
            true
        }
    }

    private fun onMove(event: MotionEvent) {
        if (event.historySize == 0) return
        val currentY = event.getY(0)
        if (currentY <= 0) return
        val offset = currentY - event.getHistoricalY(0, 0)
        if (offset in -10f..10f) return
        if (offset < 0) onUpGesture(offset)
        else if (offset > 0) onDownGesture(offset)
    }

    private fun onUpGesture(offset: Float) {
        action = Action.UpScroll
        val per = abs(offset) / parentView.height
        with(playerView) {
            pivotY = 0f
            pivotX = parentView.width / 2f
            scaleY -= per
            scaleX -= per
        }
        with(bottomView) {
            translationY -= abs(offset)
        }
    }

    private fun onDownGesture(offset: Float) {
        action = Action.DownScroll
        if (playerView.scaleY >= 1f) return
        val per = abs(offset) / parentView.height
        with(playerView) {
            pivotY = 0f
            pivotX = parentView.width / 2f
            scaleY = if (playerView.scaleY + per >= 1) 1f else playerView.scaleY + per
            scaleX += per
        }
        with(bottomView) {
            translationY += abs(offset)
        }
    }

    private fun onCancel() {
        action = Action.Idle
        val maxScalePerForPlayer =
            (playerView.height - bottomView.height).toFloat() / playerView.height.toFloat()
        if (playerView.scaleY >= maxScalePerForPlayer) {
            playerView.scaleY = 1f
            playerView.scaleX = 1f
            bottomView.translationY = 0f
        } else {
            playerView.scaleY = maxScalePerForPlayer
            playerView.scaleX = maxScalePerForPlayer
            bottomView.translationY = 0f
            bottomView.translationY -= bottomView.height.toFloat()
        }
    }
}