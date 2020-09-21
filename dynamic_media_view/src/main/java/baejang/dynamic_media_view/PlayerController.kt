package baejang.dynamic_media_view

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import baejang.dynamic_media_view.ui.view.TimeSeekView
import baejang.dynamic_media_view.util.log
import com.google.android.exoplayer2.Player

class PlayerController private constructor(builder: Builder) :
    Player.EventListener, LifecycleObserver {

    private val player: Player
    private val timeSeekView: TimeSeekView?
    private val lifecycle: Lifecycle?

    init {
        lifecycle = builder.lifecycle?.also { it.addObserver(this) }
        player = builder.getPlayer() ?: throw IllegalArgumentException("Player must be not null")
        timeSeekView = builder.getTimeSeekView()?.apply {
            setPlayer(player)
            if (this !is View) throw IllegalArgumentException()
        }
    }

    class Builder(val lifecycle: Lifecycle? = null) {

        private var player: Player? = null
        private var timeSeekView: TimeSeekView? = null

        fun setPlayer(player: Player): Builder {
            this.player = player
            return this
        }

        fun setSeekBarView(timeSeekView: TimeSeekView): Builder {
            this.timeSeekView = timeSeekView
            return this
        }

        fun getPlayer() = player

        fun getTimeSeekView() = timeSeekView

        fun build() = PlayerController(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        log("start")
        player.addListener(this)
        timeSeekView?.start()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stop() {
        log("stop")
        player.removeListener(this)
        timeSeekView?.stop()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun release() {
        log("release")
        lifecycle?.removeObserver(this)
        player.removeListener(this)
        timeSeekView?.stop()
    }
}