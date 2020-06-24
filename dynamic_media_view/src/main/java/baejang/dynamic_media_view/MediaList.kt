package baejang.dynamic_media_view

interface MediaList<T : Preview> {

    fun setItems(items: List<T>)
}

class BasicMediaList : MediaList<BasicPreview> {

    val items = mutableListOf<BasicPreview>()

    override fun setItems(items: List<BasicPreview>) {
        this.items.clear()
        this.items.addAll(items)
    }
}