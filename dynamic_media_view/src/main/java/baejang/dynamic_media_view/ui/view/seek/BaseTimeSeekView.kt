package baejang.dynamic_media_view.ui.view.seek

import android.content.Context
import android.util.AttributeSet
import android.view.View
import baejang.dynamic_media_view.R
import baejang.dynamic_media_view.ui.view.TimeSeekView
import com.google.android.exoplayer2.Player

// TODO : 짜잘한 Attributes 추가
abstract class BaseTimeSeekView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), TimeSeekView {

    protected val typedArray = context.theme.obtainStyledAttributes(
        attrs, R.styleable.BaseTimeSeekView, 0, 0
    )
    protected var _player: Player? = null
    private var isStarted: Boolean = false


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
}