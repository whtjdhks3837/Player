package baejang.dynamic_media_view.ui

import baejang.dynamic_media_view.ui.view.Hideable
import baejang.dynamic_media_view.ui.view.PlayerControllerView

class AutoHideHelper(private val views: Set<PlayerControllerView>) : Runnable {

    private val _hideableViews = views.filter {
        if (it is Hideable) it.isHideEnabled() or it.isAutoHide()
        else false
    }
    private var isEnabled = true

    init {
        for (view in _hideableViews) if (view is Hideable) {
            view.onEnabled = { isEnabled ->
                this.isEnabled = isEnabled
                if (isEnabled) view.postDelayed(this, view.hideDelay())
                else _hideableViews.forEach { it.removeCallbacks(this) }
            }
            if (view.isAutoHide()) view.postDelayed(this, view.hideDelay())
            else view.removeCallbacks(this)
        }
    }

    override fun run() {
        if (!isEnabled) return
        for (view in _hideableViews) if (view is Hideable) {
            if (view.isAutoHide()) view.hide()
            view.removeCallbacks(this)
        }
    }

    fun showAndHide() {
        _hideableViews.forEach { it.removeCallbacks(this) }
        val anyShow = _hideableViews.any { if (it is Hideable) it.isShow() else false }
        if (anyShow) for (view in _hideableViews) {
            if (view is Hideable) view.hide()
        } else for (view in _hideableViews) if (view is Hideable) {
            view.show()
            if (view.isAutoHide()) view.postDelayed(this, view.hideDelay())
        }
    }
}
