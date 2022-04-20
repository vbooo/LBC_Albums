package com.lbcalbums.data.repository

import com.lbcalbums.data.NetworkUtils
import com.lbcalbums.data.datasource.AlbumRemoteDatasource
import com.lbcalbums.domain.repository.AlbumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.lbcalbums.domain.Result
import com.lbcalbums.domain.model.Album
import javax.inject.Inject

/**
 * This class is the implementation of [AlbumRepository]
 */
class AlbumRepositoryImpl @Inject constructor(
    private val albumRemoteDatasource: AlbumRemoteDatasource,
    private val networkUtils: NetworkUtils
) : AlbumRepository {

    override fun getAlbums(): Flow<Result<List<Album>>> = flow {
        emit(Result.Loading)

        if (networkUtils.hasNetworkConnection()) {
            try {
                val listRemote = albumRemoteDatasource.getAlbums()
                emit(Result.Success(data = mapToAlbumModel(listRemote)))
            } catch (e: Exception) {
                emit(Result.Error(exception = e))
            }
        } else {
            emit(Result.Error(exception = null))
        }
    }

    private fun mapToAlbumModel(listAlbum: List<com.lbcalbums.data.db.entity.Album>): List<Album> {
        val listToReturn = mutableListOf<Album>()
        for (item in listAlbum) {
            listToReturn.add(
                Album(
                   item.id,
                   item.albumId,
                   item.title,
                   item.url,
                   item.thumbnailUrl,
                )
            )
        }
        return listToReturn
    }
}
