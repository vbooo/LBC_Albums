package com.lbcalbums.data.network

import com.lbcalbums.domain.model.Album
import retrofit2.http.GET

interface AlbumService {

    @GET("img/shared/technical-test.json")
    suspend fun getAlbums(): List<Album>
}
