package com.jd.newsletter.ui.data.remote

import com.jd.newsletter.ui.data.model.NewsModelResponse
import io.reactivex.Single

import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {

    @GET("v3/noticias/")
    fun getNewsLetter(
        @Query("page") page: Int,
    ): Single<NewsModelResponse>
}