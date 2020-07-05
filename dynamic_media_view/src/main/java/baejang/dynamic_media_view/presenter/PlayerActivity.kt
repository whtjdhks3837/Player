package baejang.dynamic_media_view.presenter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import baejang.dynamic_media_view.PlayerManager
import baejang.dynamic_media_view.R
import baejang.dynamic_media_view.data.media.source.MediaSourceProvider
import baejang.dynamic_media_view.view.BasicControllerView
import baejang.dynamic_media_view.view.ControllerType
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.RandomTrackSelection
import com.google.android.exoplayer2.ui.DebugTextViewHelper
import com.google.android.exoplayer2.util.EventLogger
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: SimpleExoPlayer
    private val mediaSourceProvider = PlayerManager.getMediaSourceProvider()
    private val eventLogger = EventLogger(DefaultTrackSelector(RandomTrackSelection.Factory()))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        mediaPlayer = ExoPlayerFactory.newSimpleInstance(this).apply {
            addAnalyticsListener(eventLogger)
            playWhenReady = true
            playerView.player = this
        }
        initControllerView()
        DebugTextViewHelper(mediaPlayer, debugText).start()
        mediaPlayer.prepare(mediaSourceProvider.getMediaSource())
    }

    private fun initControllerView() {
        playerView.useController = false
        if (mediaSourceProvider !is MediaSourceProvider.Multiple) {
            return
        }
        when (intent.getIntExtra(PlayerManager.CONTROLLER_TYPE, -1)) {
            ControllerType.Basic.value -> BasicControllerView(playerView, mediaSourceProvider)
            else -> playerView.useController = true
        }
    }

    override fun onResume() {
        super.onResume()
        playerView.onResume()
    }

    override fun onPause() {
        super.onPause()
        playerView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}