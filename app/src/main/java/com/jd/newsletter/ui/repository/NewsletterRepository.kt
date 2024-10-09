package com.jd.newsletter.ui.repository

import com.jd.newsletter.ui.data.remote.ServiceApi
import javax.inject.Inject

class NewsletterRepository @Inject constructor(
    private val api: ServiceApi
) {
    fun getNewsLetter() = api.getNewsLetter()
}