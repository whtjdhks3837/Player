package baejang.dynamic_media_view.data.media

interface Media

data class BaseMedia(
    val name: String,
    val url: String,
    val extension: String
): Media