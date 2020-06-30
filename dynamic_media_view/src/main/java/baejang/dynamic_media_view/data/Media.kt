package baejang.dynamic_media_view.data

interface Media

data class BaseMedia(
    val name: String,
    val url: String,
    val extension: String
): Media