package com.route.newsc39.data.repos.news_repo

import com.route.newsc39.api.model.Article
import com.route.newsc39.api.model.Source

interface NewsRepo{
    suspend  fun loadArticles(apiKey: String, sourceId: String): List<Article?>

    suspend fun loadSources(apiKey: String, categoryId: String): List<Source?>

}