package baejang.dynamic_media_view.presenter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import baejang.dynamic_media_view.PlayerManager
import baejang.dynamic_media_view.R
import baejang.dynamic_media_view.data.media.BaseMedia
import baejang.dynamic_media_view.data.media.source.ConcatenatingMediaSourceImpl
import baejang.dynamic_media_view.data.media.hlsVideoUrl1
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.RandomTrackSelection
import com.google.android.exoplayer2.ui.DebugTextViewHelper
import com.google.android.exoplayer2.util.EventLogger
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: SimpleExoPlayer
    private lateinit var mediaSourceProvider: ConcatenatingMediaSourceImpl.Provider
    private lateinit var mediaSource: ConcatenatingMediaSourceImpl
    private val eventLogger = EventLogger(DefaultTrackSelector(RandomTrackSelection.Factory()))

    private val mediaSet = setOf(
        BaseMedia(
            "hi",
            hlsVideoUrl1, "m3u8"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        mediaPlayer = ExoPlayerFactory.newSimpleInstance(this).apply {
            addAnalyticsListener(eventLogger)
        }
        mediaSourceProvider = ConcatenatingMediaSourceImpl.Provider().apply {
            setItems(mediaSet)
        }
        mediaSource =
            ConcatenatingMediaSourceImpl(
                mediaSourceProvider, PlayerManager.getDataSourceFactory()
            )

        playerView.player = mediaPlayer.apply {
            playWhenReady = true
        }
        DebugTextViewHelper(mediaPlayer, debugText).start()
        mediaPlayer.prepare(mediaSource.mediaSource)
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