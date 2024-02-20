package com.fermion.android.base.view

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.fermion.android.base.constants.ProgressState
import com.fermion.android.base.helper.BaseProgressBar
import com.fermion.android.base.helper.BaseProgressBarProvider

/**
 * Created by Bhavesh Auodichya.
 *
 * BaseActivity Class extends AppCompactActivity
 *
 * **Note**: This provides both view-binding and view-model injection.
 *
 * Extend this class with your activity class to reduce boiler plate code
 *
 * **Info** : While defining your activity annotate it with AndroidEntryPoint to
 * generate activity injection code
 *
 * @property binding use this to access view of activity.
 * @property viewModel use this to access view model.
 *
 * @since 1.0.0
 */
abstract class BaseActivity<B : ViewBinding, V : BaseViewModel> : AppCompatActivity(),
    BaseProgressBarProvider {

    private var mProgressDialog: Dialog? = null

    lateinit var viewModel: V

    private var _binding: B? = null

    protected val binding get() = requireNotNull(_binding)

    abstract val bindingInflater: (LayoutInflater) -> B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(_binding?.root)
        viewModel = obtainViewModel().value
        observeProgressBar()
    }

    private fun observeProgressBar() {
        viewModel.showProgress.observe(this) {
            if (it.equals(ProgressState.SHOW)) {
                showProgressBar()
            } else {
                hideProgressBar()
            }

        }
    }

    abstract fun obtainViewModel(): Lazy<V>


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun hideProgressBar() {
        mProgressDialog?.let { dialog ->
            if (dialog.isShowing) {
                dialog.cancel()
            }
        }
    }

    override fun showProgressBar() {
        hideProgressBar()
        mProgressDialog = BaseProgressBar().showLoading(this)
    }

}

/**  * Extension function to simplify setting an afterTextChanged action to EditText components.  */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

fun EditText.textString(): String {
    return this.text.toString()
}
