package com.jd.newsletter.ui.repository

import androidx.paging.PagingData
import com.jd.newsletter.ui.data.model.NewsModel
import com.jd.newsletter.ui.dataSource.NewsletterRemoteDataSourceImpl
import io.reactivex.Flowable

class NewsletterRepositoryImpl(
    private val newsletterRemoteDataSourceImpl: NewsletterRemoteDataSourceImpl
) : NewsletterRepository {
    override fun getNewsLetter(): Flowable<PagingData<NewsModel>> =
        newsletterRemoteDataSourceImpl.getNewsLetter()

}