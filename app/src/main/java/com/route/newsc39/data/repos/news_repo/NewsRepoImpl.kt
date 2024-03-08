package com.route.newsc39.data.repos.news_repo

import com.route.newsc39.api.model.Article
import com.route.newsc39.api.model.Source
import com.route.newsc39.data.ConnectivityChecker
import com.route.newsc39.data.repos.news_repo.data_sources.local_data_source.LocalDataSource
import com.route.newsc39.data.repos.news_repo.data_sources.remote_data_source.RemoteDataSource

class NewsRepoImpl(val remoteDataSource: RemoteDataSource,
                   val localDataSource: LocalDataSource, ): NewsRepo {
    override suspend fun loadArticles(apiKey: String, sourceId: String): List<Article?> {
        if(ConnectivityChecker.isNetworkAvailable()){
           return remoteDataSource.loadArticles(apiKey, sourceId).articles!!;
        }else {
           return localDataSource.loadArticles(sourceId)
        }
    }

    override suspend fun loadSources(apiKey: String, categoryId: String): List<Source?> {
        if(ConnectivityChecker.isNetworkAvailable()){
            val response = remoteDataSource.loadSources(apiKey, categoryId)
            response.sources?.let {
                localDataSource.saveSources(it)
            }
            return response.sources!!
        }else {
            return localDataSource.loadSources(categoryId)
        }
    }
}