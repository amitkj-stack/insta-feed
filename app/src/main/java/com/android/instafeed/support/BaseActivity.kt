package com.android.instafeed.support

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.instafeed.support.Utils.hideKeyboard
import com.android.instafeed.support.Utils.showSnackMessage
import com.google.android.material.snackbar.Snackbar


abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity(), FragmentCommunicator {
    abstract val layoutId: Int
    lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<B>(this, layoutId)
    }

    override fun frgShowSnackBarMessage(message: String?) {
        showSnackMessage(message)
    }
}