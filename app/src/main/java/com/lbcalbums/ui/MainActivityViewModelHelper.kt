package com.lbcalbums.ui

import android.content.Context
import android.view.View
import com.lbcalbums.R
import com.lbcalbums.domain.model.Album

/**
 * This class provides UI related informations to avoid put this kind of logic in the MainActivity, and test it more easily
 */
class MainActivityViewModelHelper(var context: Context, var listAlbum: List<Album> = listOf(), var isError: Boolean = false, var isConnectedToNetwork: Boolean) {
    /**
     * Get the ProgressBar visibility
     */
    fun getProgressBarVisibility(): Int {
        return if (listAlbum.isEmpty() && !isConnectedToNetwork || listAlbum.isNotEmpty() || isError) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    /**
     * Get the message value
     */
    fun getMessageValue(): String? {
        return if (listAlbum.isEmpty() && !isConnectedToNetwork) {
            context.resources.getString(R.string.message_no_data)
        } else if (isError) {
            context.resources.getString(R.string.error_get_albums)
        } else {
            null
        }
    }

    /**
     * Get the message visibility
     */
    fun getMessageVisibility(): Int {
        return if (isError || (listAlbum.isEmpty() && !isConnectedToNetwork)) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}
