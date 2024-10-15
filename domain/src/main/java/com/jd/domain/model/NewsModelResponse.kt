package com.jd.domain.model

data class NewsModelResponse(
    val count: Long,
    val page: Long,
    val totalPages: Long,
    val nextPage: Long,
    val previousPage: Long,
    val showingFrom: Long,
    val showingTo: Long,
    val items: List<NewsModel>
)
