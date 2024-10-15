package com.jd.data.repository.dataSource

import androidx.paging.PagingData
import com.jd.domain.model.NewsModel
import io.reactivex.Flowable

interface NewsletterRemoteDataSource {
    fun getNewsLetter(): Flowable<PagingData<NewsModel>>
}
