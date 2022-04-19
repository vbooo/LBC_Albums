package com.lbcalbums.data.repository

import com.lbcalbums.domain.Result
import com.lbcalbums.domain.model.Album
import com.lbcalbums.domain.repository.AlbumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * This class is the implementation of [AlbumRepository]
 */
class AlbumRepositoryImpl @Inject constructor() : AlbumRepository {

    override fun getAlbums(): Flow<Result<List<Album>>> = flow {
        emit(Result.Loading)

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

        emit(Result.Success(data = listLoaded))
    }
}
