package com.jd.domain.useCase

import com.jd.domain.repository.NewsletterRepository

class GetNewsLetterUseCase(private val newsLetterRepository: NewsletterRepository) {
    operator fun invoke() = newsLetterRepository.getNewsLetter()
}
