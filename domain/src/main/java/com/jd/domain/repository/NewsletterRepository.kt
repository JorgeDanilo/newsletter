package com.jd.domain.repository

import androidx.paging.PagingData
import com.jd.domain.model.NewsModel
import io.reactivex.Flowable

interface NewsletterRepository {
    fun getNewsLetter(): Flowable<PagingData<NewsModel>>
}