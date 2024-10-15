package com.jd.data.api

import com.jd.domain.model.NewsModelResponse
import io.reactivex.Single

import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {

    @GET("v3/noticias/")
    fun getNewsLetter(
        @Query("page") page: Int,
    ): Single<NewsModelResponse>
}