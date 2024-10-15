package com.jd.data.repository

import androidx.paging.PagingData
import com.jd.domain.repository.NewsletterRepository
import com.jd.data.repository.dataSourceImpl.NewsletterRemoteDataSourceImpl
import io.reactivex.Flowable

class NewsletterRepositoryImpl(
    private val newsletterRemoteDataSourceImpl: NewsletterRemoteDataSourceImpl
) : NewsletterRepository {
    override fun getNewsLetter(): Flowable<PagingData<com.jd.domain.model.NewsModel>> =
        newsletterRemoteDataSourceImpl.getNewsLetter()

}