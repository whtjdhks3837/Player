package baejang.dynamic_media_view

interface Preview

data class BasicPreview(
    val thumbnailUrl: String,
    val title: String
) : Preview