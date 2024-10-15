package com.jd.data.repository.dataSourceImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.jd.data.repository.dataSource.NewsletterRemoteDataSource
import com.jd.data.paging.GetNewsletterPagingSource
import io.reactivex.Flowable
import javax.inject.Inject

class NewsletterRemoteDataSourceImpl @Inject constructor(
    private val pagingSource: GetNewsletterPagingSource
) : NewsletterRemoteDataSource {
    override fun getNewsLetter(): Flowable<PagingData<com.jd.domain.model.NewsModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
                maxSize = 30,
                initialLoadSize = 10,
                prefetchDistance = 1
            ),
            pagingSourceFactory = { pagingSource }
        ).flowable
    }
}