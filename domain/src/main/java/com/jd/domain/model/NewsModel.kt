package com.jd.domain.model

import com.google.gson.annotations.SerializedName

data class NewsModel(
    val id: Long,
    @SerializedName("tipo")
    val type: String? = null,
    @SerializedName("titulo")
    val title: String? = null,
    @SerializedName("introducao")
    val introduction: String? = null,
    @SerializedName("data_publicacao")
    val datePublication: String? = null,
    @SerializedName("produto_id")
    val productId: Long? = null,
    @SerializedName("produtos")
    val products: String? = null,
    @SerializedName("editoriais")
    val editorials: String? = null,
    @SerializedName("imagens")
    val image: String? = null,
    @SerializedName("produtos_relacionados")
    val productsRelated: String? = null,
    @SerializedName("destaque")
    val highlights: Boolean? = null,
    val link: String? = null,
)
