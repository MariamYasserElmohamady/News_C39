package com.route.newsc39.data.repos.news_repo.data_sources.local_data_source

import com.route.newsc39.api.model.Article
import com.route.newsc39.api.model.ArticlesResponse
import com.route.newsc39.api.model.Source
import com.route.newsc39.api.model.SourcesResponse

interface LocalDataSource {
    suspend  fun loadArticles( sourceId: String): List<Article>

    suspend fun loadSources( categoryId: String): List<Source?>

    suspend fun saveSources(sourcesList: List<Source?>)
}