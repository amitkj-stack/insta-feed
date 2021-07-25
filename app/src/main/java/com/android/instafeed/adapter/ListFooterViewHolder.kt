package com.android.instafeed.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.instafeed.R
import com.android.instafeed.databinding.ItemListFooterBinding
import com.android.instafeed.network.NetworkState

class ListFooterViewHolder(private val binding: ItemListFooterBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(status: NetworkState?) {
        binding.progressBar.visibility = if (status == NetworkState.RUNNING) VISIBLE else View.INVISIBLE
        binding.txtError.visibility = if (status == NetworkState.FAILED) VISIBLE else View.INVISIBLE
    }

    companion object {
        fun create(retry: () -> Unit, parent: ViewGroup): ListFooterViewHolder {
            val binding = DataBindingUtil.inflate<ItemListFooterBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_list_footer, parent, false
            )
            binding.txtError.setOnClickListener { retry() }
            return ListFooterViewHolder(binding)
        }
    }
}