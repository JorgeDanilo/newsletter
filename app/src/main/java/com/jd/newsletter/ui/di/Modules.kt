package com.jd.newsletter.ui.di

import com.jd.newsletter.ui.data.remote.GetNewsletterPagingSource
import com.jd.data.api.ServiceApi
import com.jd.newsletter.ui.dataSource.NewsletterRemoteDataSource
import com.jd.newsletter.ui.dataSource.NewsletterRemoteDataSourceImpl
import com.jd.newsletter.ui.repository.NewsletterRepository
import com.jd.newsletter.ui.repository.NewsletterRepositoryImpl
import com.jd.newsletter.ui.useCase.GetNewsLetterUseCase
import com.jd.newsletter.ui.useCase.UseCaseNewsletter
import com.jd.newsletter.ui.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Modules {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideServiceApi(retrofit: Retrofit): com.jd.data.api.ServiceApi {
        return retrofit.create(com.jd.data.api.ServiceApi::class.java)
    }

    @Provides
    fun provideGetNewsletterPagingSource(serviceApi: com.jd.data.api.ServiceApi): GetNewsletterPagingSource {
        return GetNewsletterPagingSource(serviceApi)
    }

    @Provides
    fun provideNewsletterRemoteDataSource(pagingSource: GetNewsletterPagingSource): NewsletterRemoteDataSource {
        return NewsletterRemoteDataSourceImpl(pagingSource)
    }

    @Provides
    fun provideNewsletterRepository(pagingSource: NewsletterRemoteDataSourceImpl): NewsletterRepository {
        return NewsletterRepositoryImpl(pagingSource)
    }

    @Provides
    fun provideUseCaseNewsletter(newsletterRepository: NewsletterRepository) = UseCaseNewsletter(
        GetNewsLetterUseCase(newsletterRepository)
    )

}