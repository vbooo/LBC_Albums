package com.lbcalbums.data.network

import com.lbcalbums.data.db.entity.Album
import retrofit2.http.GET

interface AlbumService {

    @GET("img/shared/technical-test.json")
    suspend fun getAlbums(): List<Album>
}
