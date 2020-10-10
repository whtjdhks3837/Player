package baejang.dynamic_media_view.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import baejang.dynamic_media_view.R

class MediaListAdapter : RecyclerView.Adapter<MediaListAdapter.Holder>() {

    private val items = mutableListOf<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.content_media_item, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(items: List<Item>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class Holder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: Item) {

        }
    }

    data class Item(val dummy: Int)
}