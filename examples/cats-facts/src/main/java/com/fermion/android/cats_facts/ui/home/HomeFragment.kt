package com.fermion.android.cats_facts.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fermion.android.base.view.BaseFragment
import com.fermion.android.base.view.BaseViewModel
import com.fermion.android.cats_facts.databinding.FragmentHomeBinding


class HomeFragment : BaseFragment<FragmentHomeBinding, BaseViewModel>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.wanCatFact.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFactsFragment())
        }
    }

    override fun obtainViewModel(): Lazy<BaseViewModel> {
        return viewModels()
    }
}