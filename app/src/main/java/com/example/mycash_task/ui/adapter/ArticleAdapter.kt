package com.example.mycash_task.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mycash_task.databinding.ItemArticleBinding
import com.example.mycash_task.domain.models.Article


class ArticleAdapter(
    private val articleClickListener: ArticleClickListener
) : ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(ArticleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article, articleClickListener)
    }

    class ArticleViewHolder(private val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article, articleClickListener: ArticleClickListener) {
            binding.imgArticle.load(article.imageUrl) {
                crossfade(true)
                placeholder(android.R.color.darker_gray)
                error(android.R.color.holo_red_dark)
            }
            binding.tvArticleTitle.text = article.title
            binding.tvArticleDescription.text = article.description
            binding.tvPublishedAt.text = article.publishedAt

            itemView.setOnClickListener {
                articleClickListener.onClick(article)
            }
        }
    }

    class ArticleClickListener(val clickListener: (article: Article) -> Unit) {
        fun onClick(article: Article) = clickListener(article)
    }

    class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url // Assuming `id` is a unique identifier for articles
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
}