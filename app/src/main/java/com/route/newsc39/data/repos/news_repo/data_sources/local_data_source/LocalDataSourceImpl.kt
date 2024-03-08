package com.route.newsc39.data.repos.news_repo.data_sources.local_data_source

import com.route.newsc39.api.model.Article
import com.route.newsc39.api.model.Source
import com.route.newsc39.data.database.MyDataBase

class LocalDataSourceImpl(val dataBase: MyDataBase) : LocalDataSource {
    override suspend fun loadArticles(sourceId: String): List<Article> {
        return listOf()
    }

    override suspend fun loadSources(categoryId: String): List<Source> {
        return dataBase.getSourcesDao().getSources(categoryId)
    }

    override suspend fun saveSources(sourcesList: List<Source?>) {
        val nonNullableList = sourcesList.filter {
            return@filter it != null
        } as List<Source>
        dataBase.getSourcesDao().addSources(nonNullableList)
    }
}