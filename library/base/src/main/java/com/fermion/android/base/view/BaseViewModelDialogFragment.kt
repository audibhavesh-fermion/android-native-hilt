package com.fermion.android.base.view

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

/**
 * Created by Bhavesh Auodichya.
 *
 * BaseViewModelDialogFragment extends BaseDialogFragment
 *
 * **Note**:This provides both view binding and view-model injection
 *
 * Extend this class with your dialog fragment class to reduce boiler plate code
 *
 * **Info** : While defining your fragment annotate it with @AndroidEntryPoint to
 * generate fragment injection code
 *
 * @property binding use this to access view of fragment
 * @property viewModel use this to access view model.
 *@since 1.0.0
 */

abstract class BaseViewModelDialogFragment<B : ViewBinding, V : BaseViewModel> :
    BaseDialogFragment<B>() {

    protected lateinit var viewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = obtainViewModel().value
    }


    abstract fun obtainViewModel(): Lazy<V>


    @CallSuper
    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        Navigation.setViewNavController(view, parentFragment?.findNavController())
        super.onViewCreated(view, savedInstanceState)
    }

}