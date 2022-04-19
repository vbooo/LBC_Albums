package com.lbcalbums.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lbcalbums.R
import com.lbcalbums.domain.model.Album

/**
 * This class handles the albums list
 */
class AlbumAdapter : RecyclerView.Adapter<AlbumHolder>() {

    // This list contains all the Albums
    var listAlbums: List<Album>? = null
        set(value) {
            value?.let {
                field = value
                notifyDataSetChanged()
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        return AlbumHolder(view)
    }

    override fun getItemCount(): Int {
        return listAlbums?.size ?: 0
    }

    override fun onBindViewHolder(holder: AlbumHolder, position: Int) {

        listAlbums?.let { list ->

            // display the album title
            list[position].title?.let { holder.setAlbumTitle(it) }

            // display the thumbnail image
            holder.setAlbumThumbnail(list[position].thumbnailUrl)
        }
    }
}
