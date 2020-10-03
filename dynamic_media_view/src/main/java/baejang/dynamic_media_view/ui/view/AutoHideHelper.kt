package baejang.dynamic_media_view.ui.view

class AutoHideHelper(private val views: Set<PlayerControllerView>) : Runnable {

    private val _views = views.filter {
        if (it is Hideable) it.isHideEnabled() or it.isAutoHide()
        else false
    }
    private var isEnabled = true

    init {
        for (view in _views) if (view is Hideable) {
            view.onEnabled = { isEnabled ->
                this.isEnabled = isEnabled
                if (isEnabled) view.postDelayed(this, view.hideDelay())
                else _views.forEach { it.removeCallbacks(this) }
            }
            if (view.isAutoHide()) view.postDelayed(this, view.hideDelay())
            else view.removeCallbacks(this)
        }
    }

    override fun run() {
        if (!isEnabled) return
        for (view in _views) if (view is Hideable) {
            if (view.isAutoHide()) view.hide()
            view.removeCallbacks(this)
        }
    }

    fun showAndHide() {
        _views.forEach { it.removeCallbacks(this) }
        val anyHide = _views.any { if (it is Hideable) it.isHide() else false }
        if (anyHide) {
            for (view in _views) if (view is Hideable) {
                view.show()
                if (view.isAutoHide()) view.postDelayed(this, view.hideDelay())
            }
        } else for (view in _views) if (view is Hideable) view.hide()
    }
}
