package baejang.mediaview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import baejang.dynamic_media_view.BasicPreview
import baejang.dynamic_media_view.Player

class MainActivity : AppCompatActivity() {

    private val previewList = listOf(
        BasicPreview("", "")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Player.newBuilder(this)
            .setPreviewList(previewList)
            .build()
            .start()
    }
}
