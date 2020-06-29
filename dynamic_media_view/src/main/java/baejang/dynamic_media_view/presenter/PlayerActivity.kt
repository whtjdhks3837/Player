package baejang.dynamic_media_view.presenter

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import baejang.dynamic_media_view.R
import baejang.dynamic_media_view.controller.MediaPlayerController
import baejang.dynamic_media_view.controller.MediaSourceProvider
import baejang.dynamic_media_view.data.PlayList
import baejang.dynamic_media_view.databinding.ActivityPlayerBinding
import baejang.dynamic_media_view.util.toast
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.RandomTrackSelection
import com.google.android.exoplayer2.ui.DebugTextViewHelper
import com.google.android.exoplayer2.util.EventLogger
import com.google.gson.Gson

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding
    private val handler = Handler()

    private val mediaPlayer by lazy { ExoPlayerFactory.newSimpleInstance(this) }
    private val mediaSourceProvider by lazy {
        MediaSourceProvider(intent.getStringExtra("playlist")
            ?.let { Gson().fromJson(it, PlayList::class.java) } ?: PlayList(listOf()))
    }
    private val mediaController by lazy { MediaPlayerController(mediaPlayer, handler) }
    private val eventLogger = EventLogger(DefaultTrackSelector(RandomTrackSelection.Factory()))

    private val position by lazy { intent.getIntExtra("position", 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_player)
        binding.playerView.apply {
            this.player = mediaPlayer.apply {
                playWhenReady = true
            }.apply {
                addAnalyticsListener(eventLogger)
            }
        }

        mediaController.apply {
            playEndCallback = onPlayEnd()
            errorCallback = { toast("error occur") }
        }

        DebugTextViewHelper(mediaPlayer, binding.debug).start()
        mediaSourceProvider.setMediaSource(position)
        mediaPrepareAndDownload()
    }

    override fun onResume() {
        super.onResume()
        binding.playerView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.playerView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    private fun mediaPrepareAndDownload() {
        mediaPlayer.prepare(mediaSourceProvider.mediaSource)
    }

    private fun onPlayEnd(): () -> Unit = {
        if (mediaSourceProvider.hasNext()) {
            mediaSourceProvider.next()
            mediaPrepareAndDownload()
        } else {
            toast("마지막 영상입니다.")
        }
    }
}