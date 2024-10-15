package com.jd.newsletter.ui.repository

import androidx.paging.PagingData
import com.jd.newsletter.ui.data.model.NewsModel
import io.reactivex.Flowable

interface NewsletterRepository {
    fun getNewsLetter(): Flowable<PagingData<NewsModel>>
}