package baejang.dynamic_media_view

import android.content.Context
import android.content.Intent

class Player private constructor(private val builder: Builder) {

    companion object {

        @JvmStatic
        fun newBuilder(context: Context) = Builder(context)
    }

    val mediaController: MediaController?
    val previewList: List<Preview>?

    init {
        mediaController = builder.getMediaController()
        previewList = builder.getPreviewList()
    }

    fun start() {
        with(builder.context) {
            startActivity(Intent(this, MediaActivity::class.java))
        }
    }

    class Builder(val context: Context) {

        private var mediaController: MediaController? = null
        private var previewList: List<Preview>? = null

        fun setMediaController(mediaController: MediaController): Builder {
            this.mediaController = mediaController
            return this
        }

        fun setPreviewList(previewList: List<Preview>): Builder {
            this.previewList = previewList
            return this
        }

        fun getMediaController() = mediaController

        fun getPreviewList() = previewList

        fun build() = Player(this)
    }
}