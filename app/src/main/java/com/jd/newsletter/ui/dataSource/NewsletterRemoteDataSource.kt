package com.jd.newsletter.ui.dataSource

import androidx.paging.PagingData
import com.jd.newsletter.ui.data.model.NewsModel
import io.reactivex.Flowable

interface NewsletterRemoteDataSource {
    fun getNewsLetter(): Flowable<PagingData<NewsModel>>
}
