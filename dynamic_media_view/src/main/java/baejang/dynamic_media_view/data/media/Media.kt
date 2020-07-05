package baejang.dynamic_media_view.data.media

sealed class Media {
    data class Base(
        val name: String,
        val url: String
    ): Media()
}