package com.lbcalbums.domain.repository

import com.lbcalbums.domain.Result
import com.lbcalbums.domain.model.Album
import kotlinx.coroutines.flow.Flow

/**
 * Interface for define Albums contract
 */
interface AlbumRepository {
    fun getAlbums(): Flow<Result<List<Album>>>
}
