package com.lbcalbums.data.datasource

import com.lbcalbums.data.network.AlbumService
import com.lbcalbums.domain.model.Album
import javax.inject.Inject

class AlbumRemoteDatasource  @Inject constructor(private val albumService: AlbumService) {
    suspend fun getAlbums(): List<Album> {
        return albumService.getAlbums()
    }
}