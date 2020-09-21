package baejang.dynamic_media_view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import baejang.dynamic_media_view.BuildConfig
import baejang.dynamic_media_view.PlayerController
import baejang.dynamic_media_view.PlayerManager
import baejang.dynamic_media_view.R
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
    private val mediaSource = mediaSourceProvider.getMediaSource()
    private val eventLogger = EventLogger(DefaultTrackSelector(RandomTrackSelection.Factory()))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        mediaPlayer = ExoPlayerFactory.newSimpleInstance(this).apply {
            playWhenReady = true
            playerView.player = this
            prepare(mediaSource)
            if (BuildConfig.DEBUG) addAnalyticsListener(eventLogger)
        }
        PlayerController.Builder(lifecycle)
            .setPlayer(playerView.player)
            .setSeekBarView(seek_bar)
            .build()

        if (BuildConfig.DEBUG) DebugTextViewHelper(mediaPlayer, debugText).start()
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