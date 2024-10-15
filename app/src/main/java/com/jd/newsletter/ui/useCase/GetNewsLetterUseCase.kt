package com.jd.newsletter.ui.useCase

import com.jd.newsletter.ui.repository.NewsletterRepository

class GetNewsLetterUseCase(
    private val newsLetterRepository: NewsletterRepository
) {
    operator fun invoke() = newsLetterRepository.getNewsLetter()
}
