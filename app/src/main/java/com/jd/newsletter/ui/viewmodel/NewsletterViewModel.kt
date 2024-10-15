package com.jd.newsletter.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.jd.newsletter.ui.data.model.NewsModel
import com.jd.newsletter.ui.useCase.UseCaseNewsletter
import com.jd.newsletter.ui.util.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class NewsletterViewModel @Inject constructor(
    private val useCase: UseCaseNewsletter
) : ViewModel() {

    private val _newsletter =
        MutableStateFlow<ResourceState<PagingData<NewsModel>>>(ResourceState.Empty())
    val newsletter: StateFlow<ResourceState<PagingData<NewsModel>>> = _newsletter

    private val _compositeDisposable = CompositeDisposable()

    fun getNewsLetter() {
        val observable = useCase
            .getNewsletter.invoke()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .cachedIn(viewModelScope)
            .subscribe({
                _newsletter.value = ResourceState.Success(it)
            }, {
                _newsletter.value = ResourceState.Error(it.message.toString(), null)
            })

        _compositeDisposable.addAll(observable)

    }

    override fun onCleared() {
        super.onCleared()
        _compositeDisposable.clear()
    }
}