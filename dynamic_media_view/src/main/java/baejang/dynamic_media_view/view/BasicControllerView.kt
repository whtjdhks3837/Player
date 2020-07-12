package baejang.dynamic_media_view.view

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.SeekBar
import android.widget.TextView
import baejang.dynamic_media_view.R
import baejang.dynamic_media_view.controller.EventListenerWrapper
import baejang.dynamic_media_view.controller.PlayerControllerMediator
import baejang.dynamic_media_view.util.getDrawable
import baejang.dynamic_media_view.util.isPlaying
import baejang.dynamic_media_view.util.log
import baejang.dynamic_media_view.util.toast
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player.*

class BasicControllerView(
    private val root: ViewGroup,
    private val controllerMediator: PlayerControllerMediator
) : ControllerView {

    private val view: View = LayoutInflater.from(root.context)
        .inflate(R.layout.basic_controller_view, root, false).apply {
            (layoutParams as FrameLayout.LayoutParams).gravity = Gravity.BOTTOM
            root.addView(this)
        }
    private val player = controllerMediator.player
    private val multiple = controllerMediator.getMultiple()
    private val repeat = controllerMediator.getRepeat()
    private val shuffle = controllerMediator.getShuffle()
    private val eventListener = EventListenerWrapper(player)

    private val previousBtn = view.findViewById<View>(R.id.previous_btn)
    private val nextBtn = view.findViewById<View>(R.id.next_btn)
    private val playAndPauseBtn = view.findViewById<View>(R.id.play_and_pause_btn)
    private val repeatBtn = view.findViewById<View>(R.id.repeat_btn)
    private val shuffleBtn = view.findViewById<View>(R.id.shuffle_btn)
    private val seekBar = view.findViewById<SeekBar>(R.id.seek_bar)
    private val playTimeText = view.findViewById<TextView>(R.id.play_time_text)
    private val mediaTimeText = view.findViewById<TextView>(R.id.media_time_text)

    private val onPlayReady: (Boolean, Int) -> Unit = { playWhenReady, playbackState ->
        log("Ready $playWhenReady , state $playbackState")
        playAndPauseBtn.background = when (playWhenReady) {
            true -> view.context getDrawable R.drawable.ic_pause_24dp
            false -> view.context getDrawable R.drawable.ic_play_arrow_24dp
        }
    }

    private val onPlayEnd: (Boolean, Int) -> Unit = { playWhenReady, playbackState ->
        log("End $playWhenReady , state $playbackState")
        playAndPauseBtn.background = view.context getDrawable R.drawable.ic_play_arrow_24dp
    }

    private val onRepeatMode: (Int) -> Unit = { mode ->
        log("repeat mode $mode")
        with(repeatBtn) {
            background = when (mode) {
                REPEAT_MODE_OFF -> context getDrawable R.drawable.ic_repeat_off_24dp
                REPEAT_MODE_ALL -> context getDrawable R.drawable.ic_repeat_all_24dp
                REPEAT_MODE_ONE -> context getDrawable R.drawable.ic_repeat_one_24dp
                else -> return@with
            }
            context toast "repeat mode ${when (mode) {
                REPEAT_MODE_OFF -> "Off"
                REPEAT_MODE_ALL -> "All"
                REPEAT_MODE_ONE -> "One"
                else -> "invalid"
            }}"
        }
    }

    private val onShuffleEnabled: (Boolean) -> Unit = { isShuffle ->
        log("shuffle $isShuffle")
        with(shuffleBtn) {
            background = if (isShuffle) context getDrawable R.drawable.ic_shuffle_on_24dp
            else context getDrawable R.drawable.ic_shuffle_off_24dp
            context toast "shuffle $isShuffle"
        }
    }

    private val onError: (ExoPlaybackException?) -> Unit = { exception ->
        log("Error $exception")
        view.context toast "Error $exception"
    }

    init {
        setPreviousClick()
        setNextClick()
        setPlayAndPauseClick()
        setRepeatClick()
        setShuffleClick()
        eventListener.let {
            it.onPlayReadyCallback = onPlayReady
            it.onPlayEndCallback = onPlayEnd
            it.onRepeatModeCallback = onRepeatMode
            it.onShuffleEnabledCallback = onShuffleEnabled
            it.onErrorCallback = onError
        }
    }

    private fun setPreviousClick() = previousBtn.setOnClickListener {
        multiple?.apply {
            if (!previous()) it.context toast "처음 영상입니다."
        }
    }

    private fun setNextClick() = nextBtn.setOnClickListener {
        multiple?.apply {
            if (!next()) it.context toast "마지막 영상입니다."
        }
    }

    private fun setPlayAndPauseClick() = playAndPauseBtn.setOnClickListener {
        player.playWhenReady = !player.isPlaying()
    }

    private fun setRepeatClick() = repeatBtn.setOnClickListener {
        repeat?.apply {
            val newMode = when (getRepeatMode()) {
                REPEAT_MODE_OFF -> REPEAT_MODE_ALL
                REPEAT_MODE_ALL -> REPEAT_MODE_ONE
                REPEAT_MODE_ONE -> REPEAT_MODE_OFF
                else -> return@setOnClickListener
            }
            setRepeatMode(newMode)
        }
    }

    private fun setShuffleClick() = shuffleBtn.setOnClickListener {
        shuffle?.apply { shuffle.setShuffle(!isShuffle()) }
    }

    override fun start() {
        eventListener.start()
    }

    override fun pause() {
        eventListener.pause()
    }

    override fun release() {
        eventListener.release()
    }
}
