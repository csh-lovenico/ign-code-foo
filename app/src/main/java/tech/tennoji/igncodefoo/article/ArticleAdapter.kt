package tech.tennoji.igncodefoo.article

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tech.tennoji.igncodefoo.databinding.ArticleListItemBinding
import tech.tennoji.igncodefoo.databinding.ReviewArticleItemBinding
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

class ArticleAdapter(private val articleListener: ArticleListener) :
    ListAdapter<ArticleData, RecyclerView.ViewHolder>(ArticleDataDiffCallback()) {

    companion object {
        const val NORMAL_ARTICLE = 0
        const val REVIEW_ARTICLE = 1
    }

    class NormalArticleViewHolder private constructor(private val binding: ArticleListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArticleData, listener: ArticleListener) {
            binding.clickListener = listener
            binding.articleData = item
            if (item.authors.isEmpty()) {
                binding.articleAuthor.visibility = View.GONE
                binding.articleAuthorAvatar.visibility = View.GONE
                binding.authorBy.visibility = View.GONE
            }
            if (item.metadata.description.isNullOrEmpty()) {
                binding.articleDescription.visibility = View.GONE
            }
        }

        companion object {
            fun from(parent: ViewGroup): NormalArticleViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ArticleListItemBinding.inflate(inflater, parent, false)
                return NormalArticleViewHolder(binding)
            }
        }
    }

    class ReviewArticleViewHolder private constructor(private val binding: ReviewArticleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArticleData, listener: ArticleListener) {
            binding.clickListener = listener
            binding.articleData = item
            if (item.authors.isEmpty()) {
                binding.reviewAuthorName.visibility = View.GONE
                binding.reviewAuthorAvatar.visibility = View.GONE
                binding.reviewAuthorBy.visibility = View.GONE
            }
        }

        companion object {
            fun from(parent: ViewGroup): ReviewArticleViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ReviewArticleItemBinding.inflate(inflater, parent, false)
                return ReviewArticleViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val type = getItemViewType(position)
        if (type == NORMAL_ARTICLE) {
            (holder as NormalArticleViewHolder).bind(getItem(position)!!, articleListener)
        } else {
            (holder as ReviewArticleViewHolder).bind(getItem(position)!!, articleListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == NORMAL_ARTICLE) {
            NormalArticleViewHolder.from(parent)
        } else {
            ReviewArticleViewHolder.from(parent)
        }
    }

}