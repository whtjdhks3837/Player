package baejang.dynamic_media_view.ui.view.seek_controller

import android.content.Context
import android.util.AttributeSet
import baejang.dynamic_media_view.R
import baejang.dynamic_media_view.ui.view.Hideable
import baejang.dynamic_media_view.ui.view.PlayerControllerView
import baejang.dynamic_media_view.ui.view.TimeSeekController
import com.google.android.exoplayer2.Player

// TODO : 짜잘한 Attributes 추가
abstract class TimeSeekView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : PlayerControllerView(context, attrs, defStyleAttr), TimeSeekController, Hideable {

    private val typedArray = context.theme.obtainStyledAttributes(
        attrs, R.styleable.TimeSeekView, 0, 0
    )
    protected var _player: Player? = null
    private var isStarted: Boolean = false
    override var onEnabled: ((Boolean) -> Unit)? = null

    override fun start(player: Player) {
        if (isStarted) return
        isStarted = true
        if (_player == null) _player = player
        _player?.addListener(this)
        update()
    }

    override fun release() {
        if (!isStarted) return
        isStarted = false
        _player?.removeListener(this)
        removeCallbacks(this)
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        update()
    }

    override fun onPositionDiscontinuity(reason: Int) {
        update()
    }

    override fun run() {
        update()
    }

    abstract fun update()

    override fun hide() {
        visibility = GONE
    }

    override fun show() {
        visibility = VISIBLE
    }

    override fun isHide(): Boolean {
        return !isShow()
    }

    override fun isShow(): Boolean {
        return visibility == VISIBLE
    }

    override fun isHideEnabled(): Boolean {
        return typedArray.getBoolean(R.styleable.TimeSeekView_hide_enable, false)
    }

    override fun isAutoHide(): Boolean {
        return typedArray.getBoolean(R.styleable.TimeSeekView_auto_hide, false)
    }
}