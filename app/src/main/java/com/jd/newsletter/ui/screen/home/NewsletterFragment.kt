package com.jd.newsletter.ui.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.jd.domain.model.NewsModel
import com.jd.newsletter.databinding.FragmentNewsletterBinding
import com.jd.newsletter.ui.adapters.LoadingStateAdapter
import com.jd.newsletter.ui.adapters.NewsletterAdapter
import com.jd.newsletter.ui.util.ResourceState
import com.jd.newsletter.ui.util.hide
import com.jd.newsletter.ui.util.show
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsletterFragment : BaseFragment<FragmentNewsletterBinding>() {

    private val viewModel: NewsletterViewModel by viewModels()
    private val mAdapter by lazy { NewsletterAdapter() }
    private val mDisposable = CompositeDisposable()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNewsletterBinding {
        return FragmentNewsletterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let {
            viewModel.getNewsLetter()
            setupRecyclerView(it)
            observer(it)
        }
    }

    private fun setupRecyclerView(binding: FragmentNewsletterBinding) {
        binding.rcvNewsletter.let {
            it.adapter = mAdapter
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = mAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter()
            )
        }
    }

    private fun observer(binding: FragmentNewsletterBinding) = lifecycleScope.launch {
        viewModel.newsletter.collect { newsletter ->
            handleNewsletterState(binding, newsletter)
        }
    }

    private fun handleNewsletterState(
        binding: FragmentNewsletterBinding,
        newsletter: ResourceState<PagingData<NewsModel>>
    ) {
        binding.apply {
            when (newsletter) {
                is ResourceState.Success -> {
                    newsletter.data?.let {
                        tvEmptyList.hide()

                        mAdapter.addLoadStateListener { loadState ->
                            val errorState = loadState.source.append as? LoadState.Error
                                ?: loadState.source.prepend as? LoadState.Error
                                ?: loadState.append as? LoadState.Error
                                ?: loadState.prepend as? LoadState.Error

                            errorState?.let {
                                tvEmptyList.show()
                            }
                        }
                        mAdapter.submitData(lifecycle, it)
                    }
                }

                is ResourceState.Error -> {
                    tvEmptyList.show()
                }

                else -> {}
            }
        }
    }

    override fun onDestroyView() {
        mDisposable.dispose()
        super.onDestroyView()
    }
}