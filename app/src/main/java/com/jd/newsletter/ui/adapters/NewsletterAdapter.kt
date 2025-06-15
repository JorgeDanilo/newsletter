package com.jd.newsletter.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.jd.domain.model.NewsModel
import com.jd.newsletter.R
import com.jd.newsletter.databinding.NewsItemBinding
import com.jd.newsletter.ui.util.Constants
import org.json.JSONObject

class NewsletterAdapter : PagingDataAdapter<NewsModel, NewsletterAdapter.NewsletterViewHolder>(
    COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsletterViewHolder {
        return NewsletterViewHolder(
            NewsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsletterViewHolder, position: Int) {
        getItem(position)?.let { newsLetterItem ->
            holder.binding.apply {
                txtName.text = newsLetterItem.title.orEmpty()
                txtDescription.text = newsLetterItem.introduction.orEmpty()
                loadImage(newsLetterItem)
            }
        }
    }

    private fun NewsItemBinding.loadImage(newsLetterItem: NewsModel) {
        newsLetterItem.image?.let { imageJson ->
            JSONObject(imageJson).optString(IMG_INTRO).let {
                imgNews.load(Constants.BASE_URL_IMAGE + it)
            }
        } ?: run {
            imgNews.load(R.drawable.imagetest)
        }
    }

    companion object {
        private const val IMG_INTRO = "image_intro"

        private val COMPARATOR = object : DiffUtil.ItemCallback<NewsModel>() {
            override fun areItemsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    inner class NewsletterViewHolder(val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root)


}