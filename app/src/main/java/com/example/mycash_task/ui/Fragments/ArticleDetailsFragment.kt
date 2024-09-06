package com.example.mycash_task.ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.mycash_task.databinding.FragmentArticleDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailsFragment : Fragment() {
    private val args: ArticleDetailsFragmentArgs by navArgs()
    private var _binding: FragmentArticleDetailsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialize view binding
        _binding = FragmentArticleDetailsBinding.inflate(inflater, container, false)
        val article = args.Article

        // Bind data to views
        binding.textViewTitle.text = article.title
        binding.textViewAuthor.text = article.author
        binding.textViewPublishedAt.text = article.publishedAt
        binding.textViewDescription.text = article.description
        binding.textViewContent.text = article.content
        binding.textViewUrl.text = article.url
        binding.imageViewArticle.load(article.imageUrl)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clean up binding reference to avoid memory leaks
    }
}