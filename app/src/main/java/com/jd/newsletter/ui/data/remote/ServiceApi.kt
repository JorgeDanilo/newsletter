package com.jd.newsletter.ui.data.remote

import com.jd.newsletter.ui.data.model.NewsModelResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface ServiceApi {

    @GET("v3/noticias/")
    fun getNewsLetter(): Single<NewsModelResponse>
}