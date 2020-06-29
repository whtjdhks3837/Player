package baejang.dynamic_media_view.data

data class PlayList(
    val playList: List<List<Media>>
)

data class Media(
    val name: String,
    val url: String,
    val extension: String
)