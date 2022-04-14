package tech.tennoji.igncodefoo.video

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tech.tennoji.igncodefoo.databinding.VideoListItemBinding
import tech.tennoji.igncodefoo.network.VideoData

class VideoListener(val videoListener: (slug: String) -> Unit) {
    fun onClick(videoData: VideoData) = videoListener(videoData.metadata.slug)
}

class VideoDataDiffCallback : DiffUtil.ItemCallback<VideoData>() {
    override fun areItemsTheSame(oldItem: VideoData, newItem: VideoData): Boolean {
        return oldItem.contentId == newItem.contentId
    }

    override fun areContentsTheSame(oldItem: VideoData, newItem: VideoData): Boolean {
        return oldItem.metadata == newItem.metadata
    }
}

class VideoAdapter(private val videoListener: VideoListener) :
    ListAdapter<VideoData, RecyclerView.ViewHolder>(VideoDataDiffCallback()) {

    class VideoViewHolder private constructor(private val binding: VideoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VideoData, listener: VideoListener) {
            binding.clickListener = listener
            binding.videoData = item
            if (item.metadata.videoSeries == "none") {
                binding.videoItemSeries.visibility = View.INVISIBLE
            } else {
                binding.videoItemSeries.visibility = View.VISIBLE
            }
        }

        companion object {
            fun from(parent: ViewGroup): VideoViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = VideoListItemBinding.inflate(inflater, parent, false)
                return VideoViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VideoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as VideoViewHolder).bind(getItem(position)!!, videoListener)
    }
}