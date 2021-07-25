package com.android.instafeed.support

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.android.instafeed.support.Utils.showSnackMessage


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