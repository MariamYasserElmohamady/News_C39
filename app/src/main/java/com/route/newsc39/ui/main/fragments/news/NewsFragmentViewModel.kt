package com.route.newsc39.ui.main.fragments.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.route.newsc39.api.ApiManager
import com.route.newsc39.api.model.Article
import com.route.newsc39.api.model.ArticlesResponse
import com.route.newsc39.api.model.Source
import com.route.newsc39.api.model.SourcesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragmentViewModel: ViewModel() {
    val sourcesListLiveData: MutableLiveData<List<Source?>?> = MutableLiveData();
    val isLoadingLiveData: MutableLiveData<Boolean> =  MutableLiveData(false);
    val errorMessageLiveData: MutableLiveData<String> =  MutableLiveData("");
    val articlesListLiveData: MutableLiveData<List<Article?>?> = MutableLiveData();

    fun loadSources(categoryId: String) {
        isLoadingLiveData.value = true
        errorMessageLiveData.value = ""
        ApiManager.getWebServices().getSources(ApiManager.apiKey, categoryId)
            .enqueue(object : Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    isLoadingLiveData.value = false
                    if (response.isSuccessful) {
                        response.body()?.sources.let {
                            sourcesListLiveData.value = it!!
                        }

                    } else {
                        val response =
                            Gson().fromJson(
                                response.errorBody()?.string(),
                                SourcesResponse::class.java
                            )
                        errorMessageLiveData.value = response.message ?: "Try again later"
                    }
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                      isLoadingLiveData.value = false
                    errorMessageLiveData.value = t.localizedMessage ?: "Try again later"
                }
            })
    }

    fun loadArticles(sourceId: String) {
        ApiManager.getWebServices().getArticles(
            ApiManager.apiKey,
            sourceId
        ).enqueue(object : Callback<ArticlesResponse> {
            override fun onResponse(
                call: Call<ArticlesResponse>,
                response: Response<ArticlesResponse>
            ) {
                if (response.isSuccessful && response.body()?.articles?.isNotEmpty() == true) {
                    articlesListLiveData.value = response.body()?.articles!!
                } else {
                    val response =
                        Gson().fromJson(
                            response.errorBody()?.string(),
                            ArticlesResponse::class.java
                        )

                    errorMessageLiveData.value = response.message ?: "Try again later"
                }
            }
            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                isLoadingLiveData.value = false
                errorMessageLiveData.value = t.localizedMessage ?: "Try again later"
            }
        })
    }
}