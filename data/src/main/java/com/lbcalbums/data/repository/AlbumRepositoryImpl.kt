package com.lbcalbums.data.repository

import com.lbcalbums.data.NetworkUtils
import com.lbcalbums.data.datasource.AlbumLocalDatasource
import com.lbcalbums.data.datasource.AlbumRemoteDatasource
import com.lbcalbums.domain.Result
import com.lbcalbums.domain.model.Album
import com.lbcalbums.domain.repository.AlbumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

/**
 * This class is the implementation of [AlbumRepository]
 */
class AlbumRepositoryImpl @Inject constructor(
    private val albumRemoteDatasource: AlbumRemoteDatasource,
    private val albumLocalDatasource: AlbumLocalDatasource,
    private val networkUtils: NetworkUtils
) : AlbumRepository {

    override fun getAlbums(): Flow<Result<List<Album>>> = flow {
        emit(Result.Loading)

        if (networkUtils.hasNetworkConnection()) {
            try {
                // get the album from remote source
                val listRemote = albumRemoteDatasource.getAlbums()

                // we emit the result now, because we don't need to wait the local saving
                emit(Result.Success(data = mapToAlbumModel(listRemote)))

                // first, we delete the albums
                albumLocalDatasource.deleteAll()

                // then we save the albums in Room
                albumLocalDatasource.insertAlbums(listRemote)
            } catch (e: Exception) {
                emit(Result.Error(exception = e))
            }
        } else {
            Timber.i("No Network. We get the local data")
            val localList = albumLocalDatasource.getAll()
            emit(Result.Success(data = mapToAlbumModel(localList)))
        }
    }

    /**
     * Mapping function from Album Room entity to Album model
     */
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
