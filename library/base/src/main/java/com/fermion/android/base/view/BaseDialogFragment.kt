package com.fermion.android.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

/**
 * Created by Bhavesh Auodichya.
 * Base Dialog Fragment extends  Dialog Fragment
 *
 * **Note**:This provides only view binding injection
 *
 * Extend this class with your dialog fragment class to reduce boiler plate code
 *
 * **Info** : While defining your fragment annotate it with AndroidEntryPoint to
 * generate fragment injection code
 *
 * @property binding use this to access view of fragment.
 *
 * @since 1.0.0
 */
abstract class BaseDialogFragment<B : ViewBinding> : DialogFragment() {
    private var _binding: B? = null
    protected val binding get() = requireNotNull(_binding)

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}