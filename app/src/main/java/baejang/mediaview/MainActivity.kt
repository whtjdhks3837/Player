package baejang.mediaview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import baejang.dynamic_media_view.PlayerManager
import baejang.dynamic_media_view.data.*
import baejang.dynamic_media_view.data.media.Media
import baejang.dynamic_media_view.view.ControllerType

class MainActivity : AppCompatActivity() {

    private val mediaSet = setOf(
        Media.Base("1", mp4VideoUrl),
        Media.Base("2", hlsVideoUrl),
        Media.Base("3", webmVideoUrl),
        Media.Base("4", assetApiVideo)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val params = PlayerManager.Params.Builder<Media.Base>()
            .setMediaSet(mediaSet)
            .setControllerType(ControllerType.Basic)
            .build()
        PlayerManager.start(this, params)
    }
}