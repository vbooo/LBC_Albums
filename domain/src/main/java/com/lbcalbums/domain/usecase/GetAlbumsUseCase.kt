package com.lbcalbums.domain.usecase

import com.lbcalbums.domain.IoDispatcher
import com.lbcalbums.domain.Result
import com.lbcalbums.domain.model.Album
import com.lbcalbums.domain.repository.AlbumRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlbumsUseCase @Inject constructor(
    private val repository: AlbumRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, List<Album>>(dispatcher) {

    override fun execute(parameters: Unit): Flow<Result<List<Album>>> {
        return repository.getAlbums()
    }
}
