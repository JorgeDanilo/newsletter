package com.jd.newsletter.ui.data.remote

import com.jd.newsletter.ui.data.model.NewsModelResponse
import retrofit2.Response
import retrofit2.http.GET

interface ServiceApi {

    @GET("/v3/noticias")
    fun getNewsLetter(): Response<NewsModelResponse>
}