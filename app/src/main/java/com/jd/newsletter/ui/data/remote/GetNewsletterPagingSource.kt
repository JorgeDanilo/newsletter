package com.jd.newsletter.ui.data.remote

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.jd.newsletter.ui.data.model.NewsModel
import com.jd.newsletter.ui.data.model.NewsModelResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class GetNewsletterPagingSource(
    private val service: com.jd.data.api.ServiceApi
) : RxPagingSource<Int, NewsModel>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, NewsModel>> {
        val position = params.key ?: 1

        return service.getNewsLetter(position)
            .subscribeOn(Schedulers.io())
            .map { toLoadResult(it, position) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(
        data: NewsModelResponse,
        position: Int
    ): LoadResult<Int, NewsModel> {
        return LoadResult.Page(
            data = data.items,
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (position.toLong() == data.totalPages) null else position + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, NewsModel>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

}