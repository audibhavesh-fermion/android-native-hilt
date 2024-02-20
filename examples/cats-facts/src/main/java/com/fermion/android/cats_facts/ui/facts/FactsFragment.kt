package com.fermion.android.cats_facts.ui.facts

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.fermion.android.base.helper.BaseProgressBarProvider
import com.fermion.android.base.view.BaseFragment
import com.fermion.android.cats_facts.R
import com.fermion.android.cats_facts.databinding.FragmentFactsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FactsFragment : BaseFragment<FragmentFactsBinding, FactsViewModel>(), BaseProgressBarProvider {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFactsBinding
        get() = FragmentFactsBinding::inflate



    private var mProgressDialog: Dialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModel.getCatFact()
        observeCatFact()
    }

    private fun initView() {
        binding.giveMeFact.setOnClickListener {
            viewModel.getCatFact()
        }
        binding.enoughWithFacts.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeCatFact() {
        viewModel.catFactListener.observe(viewLifecycleOwner) {
            if (lifecycle.currentState != Lifecycle.State.RESUMED) {
                return@observe
            }
            if (it.isSuccess) {
                binding.catFactText.text = it.data?.fact ?: "No fact found :)"
            } else {
                binding.catFactText.text = buildString { append("Oops something went wrong :(") }
            }
        }
    }

    override fun showProgressBar() {
        hideProgressBar()
        mProgressDialog = showLoading(requireContext())
    }

    override fun obtainViewModel(): Lazy<FactsViewModel> {
        return viewModels()
    }

    override fun hideProgressBar() {
        mProgressDialog?.let { dialog ->
            if (dialog.isShowing) {
                dialog.cancel()
            }
        }
    }

    private fun showLoading(context: Context): Dialog {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.cat_progress_bar)

        // Start the animation
        val gifImageView =
            dialog.findViewById<ImageView>(R.id.cat_loader)
        gifImageView.visibility = View.VISIBLE

        Glide.with(requireContext()).load(R.raw.main).circleCrop()
            .into(gifImageView)
        dialog.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawableResource(android.R.color.transparent)
        }

        // Show the dialog
        dialog.show()
        return dialog
    }
}