package baejang.dynamic_media_view.presenter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import baejang.dynamic_media_view.R
import com.google.gson.Gson
import baejang.dynamic_media_view.data.PlayList
import baejang.dynamic_media_view.databinding.ActivityMediaListBinding
import baejang.dynamic_media_view.databinding.MediaListItemBinding
import test.exoplayer.playlist

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMediaListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_media_list)
        binding.list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ListAdapter(playlist)
        }
    }

    class ListAdapter(private val items: PlayList) :
        RecyclerView.Adapter<ListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context), R.layout.media_list_item, parent, false
                )
            )

        override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items)

        override fun getItemCount() = items.playList.size

        class ViewHolder(
            private val binding: MediaListItemBinding
        ) : RecyclerView.ViewHolder(binding.root) {

            @SuppressLint("SetTextI18n")
            fun bind(items: PlayList) {
                binding.text.text = "${binding.text.text} $layoutPosition"
                itemView.setOnClickListener {
                    itemView.context.startActivity(
                        Intent(
                            itemView.context,
                            PlayerActivity::class.java
                        ).apply {
                            putExtra("playlist", Gson().toJson(items))
                            putExtra("position", layoutPosition)
                        })
                }
            }
        }
    }
}