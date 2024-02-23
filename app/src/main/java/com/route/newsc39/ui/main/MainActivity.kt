package com.route.newsc39.ui.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.fragment.app.Fragment
import com.route.newsc39.R
import com.route.newsc39.databinding.ActivityMainBinding
import com.route.newsc39.ui.main.fragments.categories.CategoriesFragment
import com.route.newsc39.ui.main.fragments.news.NewsFragment
import com.route.newsc39.ui.main.fragments.settings.SettingsFragment


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var categoriesFragment: CategoriesFragment
    lateinit var newsFragment: NewsFragment
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolBar)
        // init action bar drawer toggle
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this, binding.root,
            R.string.drawer_open, R.string.drawer_close
        )
        // add a drawer listener into  drawer layout
        binding.root.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        // show menu icon and back icon while drawer open
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initFragments()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, categoriesFragment, "")
            .commit()
        binding.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.settingsSideMenuItem -> {
                    showFragment(SettingsFragment())
                }

                R.id.categoriesSideMenuItem -> {
                    showFragment(categoriesFragment)
                }
            }
            binding.root.closeDrawers()
            return@setNavigationItemSelectedListener true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else {
            super.onOptionsItemSelected(item)
        }

    }

    fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment, "")
            .commit()
    }

    private fun initFragments() {
        categoriesFragment = CategoriesFragment {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, NewsFragment(it.id), "")
                .addToBackStack("")
                .commit()

        }
    }
}

///Mvvm -> Model view viewmodel
///Mvc -> Model