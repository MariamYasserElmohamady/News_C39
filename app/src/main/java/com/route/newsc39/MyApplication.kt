package com.route.newsc39

import android.app.Application
import com.route.newsc39.data.ConnectivityChecker
import com.route.newsc39.data.database.MyDataBase

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ConnectivityChecker.context = this
        MyDataBase.init(this)
    }
}