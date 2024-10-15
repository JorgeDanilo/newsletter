package com.jd.newsletter.ui.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
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
class NewsletterFragment : Fragment() {

    private val viewModel: NewsletterViewModel by viewModels()
    private val mAdapter by lazy { NewsletterAdapter() }
    private lateinit var _binding: FragmentNewsletterBinding
    private val mDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsletterBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getNewsLetter()
        setupRecyclerView()
        observer()
    }

    private fun setupRecyclerView() {
        _binding.rcvNewsletter.apply {
            this.adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
            this.adapter = mAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter()
            )
        }
    }

    private fun observer() = lifecycleScope.launch {
        viewModel.newsletter.collect { newsletter ->
            _binding.apply {
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
    }

    override fun onDestroyView() {
        mDisposable.dispose()
        super.onDestroyView()
    }
}