package com.route.newsc39.ui.main.fragments.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.gson.Gson
import com.route.newsc39.api.ApiManager
import com.route.newsc39.api.model.ArticlesResponse
import com.route.newsc39.api.model.Source
import com.route.newsc39.api.model.SourcesResponse
import com.route.newsc39.databinding.FragmentNewsBinding
import com.route.newsc39.ui.main.fragments.adapters.ArticlesAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment(val categoryId: String): Fragment(), OnTabSelectedListener {
    lateinit var viewModel: NewsFragmentViewModel
    lateinit var binding: FragmentNewsBinding
    var adapter = ArticlesAdapter(listOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[NewsFragmentViewModel::class.java]
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadSources(categoryId)
        initListeners()
        binding.articlesRecyclerView.adapter = adapter
        observeToLiveData()
    }

    private fun observeToLiveData() {
        viewModel.sourcesListLiveData.observe(viewLifecycleOwner) {
            showTabs(it!!)
        }
        viewModel.isLoadingLiveData.observe(viewLifecycleOwner){
            changeLoaderVisiblity(it)
        }
        viewModel.errorMessageLiveData.observe(viewLifecycleOwner){
            if(it.isEmpty()){
                changeErrorVisiblity(false)
            }else {
                changeErrorVisiblity(true, it)
            }
        }
        viewModel.articlesListLiveData.observe(viewLifecycleOwner){
            adapter.update(it!!)
        }

    }

    private fun initListeners() {
        binding.errorView.retryBtn.setOnClickListener {
            viewModel.loadSources(categoryId)
        }
        binding.tabLayout.addOnTabSelectedListener(this)
    }

    private fun showTabs(sources: List<Source?>) {
        sources.forEach { source ->
            val tab = binding.tabLayout.newTab()
            tab.text = source?.name
            binding.tabLayout.addTab(tab)
            tab.tag = source
        }
        binding.tabLayout.getTabAt(0)?.select()

    }

    private fun changeErrorVisiblity(isVisible: Boolean, message: String = "") {
        binding.errorView.root.isVisible = isVisible
        if (isVisible) {
            binding.errorView.errorTv.text = message
        }
    }

    private fun changeLoaderVisiblity(isVisible: Boolean) {
        binding.loadingView.isVisible = isVisible
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        val source = tab?.tag as Source?
        source?.id?.let {
            viewModel.loadArticles(it)
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabReselected(tab: TabLayout.Tab?) {
        val source = tab?.tag as Source
        source.id?.let {
            viewModel.loadArticles(it)
        }
    }
}