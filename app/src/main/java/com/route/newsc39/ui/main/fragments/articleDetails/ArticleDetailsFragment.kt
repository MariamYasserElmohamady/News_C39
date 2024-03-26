package com.route.newsc39.ui.main.fragments.articleDetails

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.route.newsc39.api.model.Article
import com.route.newsc39.api.model.Constants
import com.route.newsc39.databinding.FragmentArticleDetailsBinding

class ArticleDetailsFragment :Fragment() {
    lateinit var binding:FragmentArticleDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            bindArticle(getParcelable(it))
        }

    }

    private fun bindArticle(article: Article) {
        Glide.with(binding.root)
            .load(article.urlToImage)
            .into(binding.articleImv)
        binding.articleSourceTv.text =article.source?.name
        binding.articleTitleTv.text =article.title
        binding.articlePublishedAtTv.text =article.publishedAt
        binding.articleBodyTv.text =article.content
    }

    private fun getParcelable(arguments:Bundle):Article{
       return if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU) {
            arguments.getParcelable(Constants.PASSED_ARTICLE, Article::class.java)?:Article()
        }
        else{
            @Suppress("DEPRECATION")
            arguments.getParcelable(Constants.PASSED_ARTICLE)?:Article()
        }
    }
}