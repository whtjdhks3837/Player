package baejang.dynamic_media_view

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import baejang.dynamic_media_view.ui.view.PlayerControllerView
import baejang.dynamic_media_view.ui.view.TimeSeekView
import baejang.dynamic_media_view.util.log
import com.google.android.exoplayer2.Player

class PlayerController private constructor(builder: Builder) : LifecycleObserver {

    private val player: Player
    private val timeSeekView: TimeSeekView?
    private val playerControllerView: PlayerControllerView?
    private val lifecycle: Lifecycle?

    init {
        lifecycle = builder.lifecycle?.also { it.addObserver(this) }
        player = builder.getPlayer() ?: throw IllegalArgumentException("Player must be not null")
        timeSeekView = builder.getTimeSeekView()?.apply {
            if (this !is View) throw IllegalArgumentException()
        }
        playerControllerView = builder.getPlayerControllerView()?.apply {
            if (this !is View) throw IllegalArgumentException()
        }
    }

    class Builder(val lifecycle: Lifecycle? = null) {

        private var player: Player? = null
        private var timeSeekView: TimeSeekView? = null
        private var playerControllerView: PlayerControllerView? = null

        fun setPlayer(player: Player): Builder {
            this.player = player
            return this
        }

        fun setSeekBarView(timeSeekView: TimeSeekView): Builder {
            this.timeSeekView = timeSeekView
            return this
        }

        fun setPlayerControllerView(playerControllerView: PlayerControllerView): Builder {
            this.playerControllerView = playerControllerView
            return this
        }

        fun getPlayer() = player

        fun getTimeSeekView() = timeSeekView

        fun getPlayerControllerView() = playerControllerView

        fun build() = PlayerController(this)
    }

    fun dispatchClickToChild() {
        timeSeekView?.clickOnParent()
        playerControllerView?.clickOnParent()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        log("start")
        timeSeekView?.start(player)
        playerControllerView?.start(player)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stop() {
        log("stop")
        timeSeekView?.release()
        playerControllerView?.release()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun release() {
        log("release")
        lifecycle?.removeObserver(this)
        timeSeekView?.release()
        playerControllerView?.release()
    }
}