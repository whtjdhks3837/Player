package baejang.dynamic_media_view.ui.view

import android.view.View

// TODO : Touch move 시 auto hide 막기
class ViewHideHelper(
    private val view: View,
    private val autoHide: Boolean = false,
    private val hideDelay: Long = DEFAULT_HIDE_DELAY
) : Runnable {

    companion object {
        private const val DEFAULT_HIDE_DELAY = 5000L
    }

    init {
        if (autoHide) view.postDelayed(this, hideDelay)
        else view.removeCallbacks(this)
    }

    fun show() {
        view.visibility = View.VISIBLE
        if (autoHide) view.postDelayed(this, hideDelay)
    }

    fun hide() {
        view.visibility = View.GONE
        if (autoHide) view.removeCallbacks(this)
    }

    fun isHide() = view.visibility != View.VISIBLE

    fun isShow() = !isHide()

    override fun run() {
        if (isShow()) hide()
    }
}
