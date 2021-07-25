package com.android.instafeed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.instafeed.R
import com.android.instafeed.data.Photo
import com.android.instafeed.databinding.ItemPhotoBinding

class ImageViewHolder(private val binding: ItemPhotoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(photo: Photo?) {
        if (photo != null) {
            binding.data = photo
        }
    }

    companion object {
        fun create(parent: ViewGroup): ImageViewHolder {
            val itemBinding = DataBindingUtil.inflate<ItemPhotoBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_photo, parent, false
            )
            return ImageViewHolder(itemBinding)
        }
    }
}