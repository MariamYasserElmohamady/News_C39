package com.route.newsc39.ui.main.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.route.newsc39.databinding.FragmentCategoriesBinding
import com.route.newsc39.databinding.FragmentSettingsBinding
import com.route.newsc39.model.Category
import com.route.newsc39.ui.main.fragments.adapters.CategoriesAdapter

class SettingsFragment() : Fragment() {
    lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}