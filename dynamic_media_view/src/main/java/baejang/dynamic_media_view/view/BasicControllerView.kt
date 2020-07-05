package baejang.dynamic_media_view.view

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import baejang.dynamic_media_view.R
import baejang.dynamic_media_view.data.media.source.MediaSourceProvider
import baejang.dynamic_media_view.util.isPlaying
import baejang.dynamic_media_view.util.toast
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView

class BasicControllerView(
    private val playerView: PlayerView,
    private val mediaSourceProvider: MediaSourceProvider.Multiple<*>
) {

    private val view: View = LayoutInflater.from(playerView.context)
        .inflate(R.layout.basic_controller_view, playerView, false).apply {
            (layoutParams as FrameLayout.LayoutParams).gravity = Gravity.BOTTOM
            playerView.addView(this)
        }
    private val player = playerView.player

    private val previousBtn = view.findViewById<View>(R.id.previous_btn)
    private val nextBtn = view.findViewById<View>(R.id.next_btn)
    private val playAndPauseBtn = view.findViewById<View>(R.id.play_and_pause_btn)

    init {
        initView()
        setPreviousClick()
        setNextClick()
        playAndPauseClick()
    }

    private fun initView() {
        playAndPauseBtn.background = when (player.isPlaying()) {
            true -> ContextCompat.getDrawable(view.context, R.drawable.ic_pause_24dp)
            false -> ContextCompat.getDrawable(view.context, R.drawable.ic_play_arrow_24dp)
        }
    }

    private fun setPreviousClick() = previousBtn.setOnClickListener {
        when (mediaSourceProvider.previous()) {
            true -> player.previous()
            false -> it.context toast "처음 영상입니다."
        }
    }

    private fun setNextClick() = nextBtn.setOnClickListener {
        when (mediaSourceProvider.next()) {
            true -> player.next()
            false -> view.context toast "마지막 영상입니다."
        }
    }

    private fun playAndPauseClick() = playAndPauseBtn.setOnClickListener {
        player.playWhenReady = !player.isPlaying()
    }
}
