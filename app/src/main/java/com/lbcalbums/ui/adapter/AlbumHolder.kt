package com.lbcalbums.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lbcalbums.R
import com.lbcalbums.databinding.ItemAlbumBinding
import com.squareup.picasso.Picasso

/**
 * This class handles albums informations for each line of the list
 */
class AlbumHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemAlbumBinding.bind(view)

    /**
     * Set Album title
     */
    fun setAlbumTitle(value: String) {
        binding.itemAlbumTitle.text = value
    }

    /**
     * Set Album thumbnail image
     */
    fun setAlbumThumbnail(url: String?) {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.electrician)
            .into(binding.itemAlbumThumbnail)
    }
}
