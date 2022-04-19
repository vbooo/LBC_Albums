package com.lbcalbums.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lbcalbums.domain.model.Album

/**
 * This class holds MainActivity data
 */
class MainActivityViewModel : ViewModel() {
    var listAlbums = MutableLiveData<MutableList<Album>>()

    // load the album list
    fun loadAlbums() {
        val listLoaded = mutableListOf<Album>()
        listLoaded.addAll(
            listOf(
                Album(1, 1, "titre 1", "https://via.placeholder.com/600/c4084a", "https://via.placeholder.com/150/c4084a"),
                Album(2, 1, "titre 2", "https://via.placeholder.com/600/c4084a", "https://via.placeholder.com/150/c4084a"),
                Album(3, 1, "titre 3", "https://via.placeholder.com/600/c4084a", "https://via.placeholder.com/150/c4084a"),
                Album(4, 1, "titre 4", "https://via.placeholder.com/600/c4084a", "https://via.placeholder.com/150/c4084a"),
                Album(5, 1, "titre 5", "https://via.placeholder.com/600/c4084a", "https://via.placeholder.com/150/c4084a"),
                Album(6, 1, "titre 6", "https://via.placeholder.com/600/c4084a", "https://via.placeholder.com/150/c4084a")
            )
        )
        listAlbums.postValue(listLoaded)
    }
}
