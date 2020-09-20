package baejang.mediaview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import baejang.dynamic_media_view.PlayerManager
import baejang.dynamic_media_view.data.*
import baejang.dynamic_media_view.data.media.Media
import baejang.dynamic_media_view.ui.view.ControllerType
import kotlinx.android.synthetic.main.content_play_list_item.view.*
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    private val mediaSet = setOf(
        Media.Base("1", mp4VideoUrl),
        Media.Base("2", hlsVideoUrl),
        Media.Base("3", webmVideoUrl),
        Media.Base("4", assetApiVideo)
    )

    private val listItems = listOf(
        ListAdapter.Item("Concatenating")
    )

    private val listAdapter = ListAdapter(listItems) {
        val params = PlayerManager.Params.Builder<Media.Base>()
            .setMediaSet(mediaSet)
            .setControllerType(ControllerType.Basic)
            .build()
        PlayerManager.start(this, params)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        with(list) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = listAdapter
        }
    }

    class ListAdapter(
        private val items: List<Item>,
        private val onClick: () -> Unit
    ) : RecyclerView.Adapter<ListAdapter.Holder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            Holder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.content_play_list_item, parent, false),
                onClick
            )

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.bind(items[position])
        }

        override fun getItemCount(): Int {
            return items.size
        }

        class Holder(
            private val root: View,
            private val onClick: () -> Unit
        ) : RecyclerView.ViewHolder(root) {

            fun bind(item: Item) {
                with(itemView) {
                    title.text = item.title
                    setOnClickListener { onClick() }
                }
            }
        }

        data class Item(val title: String)
    }
}