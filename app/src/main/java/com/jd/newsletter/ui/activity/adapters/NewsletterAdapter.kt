package com.jd.newsletter.ui.activity.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.jd.newsletter.R
import com.jd.newsletter.databinding.NewsItemBinding
import com.jd.newsletter.ui.data.model.NewsModel
import com.jd.newsletter.ui.util.Constants
import org.json.JSONObject

class NewsletterAdapter : RecyclerView.Adapter<NewsletterAdapter.NewsletterViewHolder>() {

    private val _differCallback = object : DiffUtil.ItemCallback<NewsModel>() {
        override fun areItemsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
            return oldItem.id == newItem.id && oldItem.title == newItem.title &&
                    oldItem.introduction == newItem.introduction &&
                    oldItem.datePublication == newItem.datePublication &&
                    oldItem.productId == newItem.productId &&
                    oldItem.products == newItem.products &&
                    oldItem.image == newItem.image
        }
    }

    private var _differ = AsyncListDiffer(this, _differCallback)

    var newsletter: List<NewsModel>
        get() = _differ.currentList
        set(value) = _differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsletterViewHolder {
        return NewsletterViewHolder(
            NewsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = newsletter.size

    override fun onBindViewHolder(holder: NewsletterViewHolder, position: Int) {
        val newsLetter = newsletter[position]
        holder.binding.apply {
            txtName.text = newsLetter.title.orEmpty()
            txtDescription.text = newsLetter.introduction.orEmpty()

            newsLetter.image?.let { imageJson ->
                JSONObject(imageJson).optString("image_intro").let {
                    imgNews.load(Constants.BASE_URL_IMAGE + it)
                }
            } ?: run {
                imgNews.load(R.drawable.imagetest)
            }
        }
    }

    inner class NewsletterViewHolder(val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root)


}