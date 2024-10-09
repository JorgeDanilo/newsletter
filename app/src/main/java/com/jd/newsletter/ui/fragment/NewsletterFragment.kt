package com.jd.newsletter.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jd.newsletter.databinding.FragmentNewsletterBinding
import com.jd.newsletter.ui.activity.adapters.NewsletterAdapter
import com.jd.newsletter.ui.util.ResourceState
import com.jd.newsletter.ui.util.hide
import com.jd.newsletter.ui.util.show
import com.jd.newsletter.ui.viewmodel.NewsletterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsletterFragment : Fragment() {

    private val viewModel: NewsletterViewModel by viewModels()
    private val _adapter by lazy { NewsletterAdapter() }
    private lateinit var _binding: FragmentNewsletterBinding

    // equivalente ao oncreate()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("NewsletterFragment", "onCreateView()")
        _binding = FragmentNewsletterBinding.inflate(inflater, container, false)
        return _binding.root
    }

    // executa apos a view ter sido criada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("NewsletterFragment", "onViewCreated()")
        viewModel.getNewsLetter()
        setupRecyclerView()
        observer()
    }

    private fun setupRecyclerView() {
        with(_binding) {
            this.rcvNewsletter.apply {
                adapter = _adapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    private fun observer() = lifecycleScope.launch {
        viewModel.newsletter.collect { newsletter ->
            _binding.apply {
                when (newsletter) {
                    is ResourceState.Success -> {
                        newsletter.data?.let {
                            tvEmptyList.hide()
                            _adapter.newsletter = it
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
}