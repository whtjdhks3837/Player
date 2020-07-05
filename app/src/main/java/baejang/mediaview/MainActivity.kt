package baejang.mediaview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import baejang.dynamic_media_view.PlayerManager
import baejang.dynamic_media_view.data.hlsVideoUrl1
import baejang.dynamic_media_view.data.media.Media

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val params = PlayerManager.Params.Builder<Media.Base>()
            .setMediaSet(setOf(Media.Base("hi", hlsVideoUrl1)))
            .build()
        PlayerManager.start(this, params)
    }
}