package com.route.newsc39.ui.main.fragments.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.newsc39.api.ApiManager
import com.route.newsc39.api.model.Article
import com.route.newsc39.api.model.Source
import com.route.newsc39.data.database.MyDataBase
import com.route.newsc39.data.repos.news_repo.NewsRepo
import com.route.newsc39.data.repos.news_repo.NewsRepoImpl
import com.route.newsc39.data.repos.news_repo.data_sources.local_data_source.LocalDataSourceImpl
import com.route.newsc39.data.repos.news_repo.data_sources.remote_data_source.RemoteDataSourceImpl
import kotlinx.coroutines.launch

class NewsFragmentViewModel : ViewModel() {
    val newsRepo: NewsRepo = NewsRepoImpl(RemoteDataSourceImpl(), LocalDataSourceImpl(
        MyDataBase.getInstance()))
    val sourcesListLiveData: MutableLiveData<List<Source?>?> = MutableLiveData();
    val isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false);
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData("");
    val articlesListLiveData: MutableLiveData<List<Article?>?> = MutableLiveData();

    fun loadSources(categoryId: String) {

        viewModelScope.launch {
            isLoadingLiveData.value = true
            errorMessageLiveData.value = ""
            Log.e("NewsFragmentViewModel","24-  isLoadingLiveData.value = true")
            try {
                val sourcesList =
                    newsRepo.loadSources(ApiManager.apiKey,categoryId)
                isLoadingLiveData.value = false
                Log.e("NewsFragmentViewModel","29-  isLoadingLiveData.value = false")
                sourcesListLiveData.value = sourcesList
            } catch (e: Exception) {
                isLoadingLiveData.value = false
                Log.e("NewsFragmentViewModel","33-  isLoadingLiveData.value = false")
                errorMessageLiveData.value = e.localizedMessage ?: "Try again later"
            }
        }
    }

    fun loadArticles(sourceId: String) {
        viewModelScope.launch {
            try {
                isLoadingLiveData.value = true
                Log.e("NewsFragmentViewModel","43-  isLoadingLiveData.value = true")
                val articlesResponse = ApiManager.getWebServices().getArticles(
                    ApiManager.apiKey,
                    sourceId
                )
                articlesListLiveData.value = articlesResponse.articles!!
                isLoadingLiveData.value = false
                Log.e("NewsFragmentViewModel","50-  isLoadingLiveData.value = false")
            } catch (e: Exception) {
                isLoadingLiveData.value = false
                Log.e("NewsFragmentViewModel","53-  isLoadingLiveData.value = false")
                errorMessageLiveData.value = e.localizedMessage ?: "Try again later"
            }
        }
    }
}

