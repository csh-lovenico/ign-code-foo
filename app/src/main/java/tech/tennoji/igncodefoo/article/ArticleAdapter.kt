package tech.tennoji.igncodefoo.article

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tech.tennoji.igncodefoo.databinding.ArticleListItemBinding
import tech.tennoji.igncodefoo.network.ArticleData

class ArticleListener(val articleListener: (slug: String) -> Unit) {
    fun onClick(articleData: ArticleData) = articleListener(articleData.metadata.slug)
}

class ArticleDataDiffCallback : DiffUtil.ItemCallback<ArticleData>() {
    override fun areItemsTheSame(oldItem: ArticleData, newItem: ArticleData): Boolean {
        return oldItem.contentId == newItem.contentId
    }

    override fun areContentsTheSame(oldItem: ArticleData, newItem: ArticleData): Boolean {
        return oldItem == newItem
    }
}

class ArticleAdapter(val articleListener: ArticleListener) :
    ListAdapter<ArticleData, ArticleAdapter.ViewHolder>(ArticleDataDiffCallback()) {
    class ViewHolder private constructor(val binding: ArticleListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArticleData, listener: ArticleListener) {
            binding.clickListener = listener
            binding.articleData = item
            if(item.authors.isEmpty()){
                binding.articleAuthor.visibility = View.GONE
                binding.articleAuthorAvatar.visibility = View.GONE
                binding.authorBy.visibility = View.GONE
            }
            if(item.metadata.description.isNullOrEmpty()){
                binding.articleDescription.visibility = View.GONE
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ArticleListItemBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, articleListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

}