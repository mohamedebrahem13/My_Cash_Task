package com.example.mycash_task.ui.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycash_task.common.Resource
import com.example.mycash_task.databinding.FragmentNewsHomeBinding
import com.example.mycash_task.domain.models.NewsCategory
import com.example.mycash_task.ui.NewsViewModel
import com.example.mycash_task.ui.adapter.ArticleAdapter
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsHomeFragment : Fragment() {
    private var _binding: FragmentNewsHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var articleAdapter: ArticleAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialize view binding
        _binding = FragmentNewsHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chipGroup = binding.chipGroup
        var firstChip: Chip? = null

        // Add chips dynamically using NewsCategory
        NewsCategory.allCategories.forEach { category ->
            val chip = Chip(requireContext()).apply {
                text = category.name
                isCheckable = true
                isCheckedIconVisible = false
                setOnClickListener {
                    // Handle chip selection
                    onCategorySelected(category.name)
                }
            }
            chipGroup.addView(chip)

            // Set the first chip as selected
            if (firstChip == null) {
                firstChip = chip
            }
        }

        // Set the first chip as checked
        firstChip?.let {
            it.isChecked = true
            onCategorySelected(NewsCategory.allCategories.first().name)
        }

        // Set up the RecyclerView for articles
        articleAdapter = ArticleAdapter(ArticleAdapter.ArticleClickListener { selectedArticle ->
            Log.v("ArticleAdapter", "Article selected: $selectedArticle")
            // Navigate to ArticleDetailsFragment with the selected article
            val action = NewsHomeFragmentDirections.actionNewsHomeFragmentToArticleDetailsFragment(selectedArticle)
            findNavController().navigate(action)
        })

        binding.recyclerViewArticles.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewArticles.adapter = articleAdapter

        // Observe articles state
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.articlesState.collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            binding.progress.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            // Hide loading indicator and show RecyclerView
                            binding.progress.visibility  = View.GONE
                            binding.recyclerViewArticles.visibility = View.VISIBLE
                            articleAdapter.submitList(resource.data)
                        }
                        is Resource.Error -> {
                            // Handle error
                            binding.progress.visibility = View.GONE
                            binding.recyclerViewArticles.visibility = View.VISIBLE
                            Toast.makeText(requireContext(), resource.exception.message ?: "An error occurred", Toast.LENGTH_SHORT).show()

                        }
                    }
                }
            }
        }
    }

    private fun onCategorySelected(category: String) {
        viewModel.getTopHeadlines(category)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clean up binding reference to avoid memory leaks
    }
}