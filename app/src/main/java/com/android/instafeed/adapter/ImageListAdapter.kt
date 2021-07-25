package com.android.instafeed.adapter

import android.os.Bundle
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.instafeed.adapter.ImageViewHolder.Companion.create
import com.android.instafeed.data.Photo
import com.android.instafeed.network.NetworkState
import com.android.instafeed.support.AppConstants

class ImageListAdapter(private val retry: () -> Unit) :
    PagedListAdapter<Photo, RecyclerView.ViewHolder>(NewsDiffCallback) {

    private var state = NetworkState.RUNNING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        if (viewType == AppConstants.AdapterViewType.DATA_VIEW_TYPE) create(parent) else ListFooterViewHolder.create(
            retry,
            parent
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == AppConstants.AdapterViewType.DATA_VIEW_TYPE)
            (holder as ImageViewHolder).bind(getItem(position))
        else (holder as ListFooterViewHolder).bind(state)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) AppConstants.AdapterViewType.DATA_VIEW_TYPE else AppConstants.AdapterViewType.FOOTER_VIEW_TYPE
    }

    companion object {
        val NewsDiffCallback = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == NetworkState.RUNNING || state == NetworkState.FAILED)
    }

    fun setState(state: NetworkState) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }

    fun getUrlBundle(position: Int): Bundle {
        val bundle = Bundle()
        bundle.putString(AppConstants.PARAM.EXTRA_URL, getItem(position)?.link)
        return bundle
    }

    fun clear() {
        submitList(null)
        notifyDataSetChanged()
    }
}