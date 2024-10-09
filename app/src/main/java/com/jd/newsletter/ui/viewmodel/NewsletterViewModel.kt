package com.jd.newsletter.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.jd.newsletter.ui.data.model.NewsModel
import com.jd.newsletter.ui.repository.NewsletterRepository
import com.jd.newsletter.ui.util.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class NewsletterViewModel @Inject constructor(
    private val repository: NewsletterRepository
) : ViewModel() {

    private val _newsletter =
        MutableStateFlow<ResourceState<List<NewsModel>>>(ResourceState.Empty())
    val newsletter: StateFlow<ResourceState<List<NewsModel>>> = _newsletter

    private val _compositeDisposable = CompositeDisposable()

    fun getNewsLetter() {
        val observable = repository.getNewsLetter()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _newsletter.value = ResourceState.Success(it.items)
                }, {
                    _newsletter.value = ResourceState.Error(it.message.toString(), null)
                }
            )
        _compositeDisposable.addAll(observable)
    }

    override fun onCleared() {
        super.onCleared()
        _compositeDisposable.clear()
    }
}