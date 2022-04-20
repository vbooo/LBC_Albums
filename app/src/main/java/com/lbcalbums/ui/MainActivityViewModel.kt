package com.lbcalbums.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lbcalbums.domain.Result
import com.lbcalbums.domain.model.Album
import com.lbcalbums.domain.usecase.GetAlbumsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * This class holds MainActivity data
 */
@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val getAlbumsUseCase: GetAlbumsUseCase
) : ViewModel() {
    var listAlbums = MutableLiveData<List<Album>>()
    val getAlbumsError: MutableLiveData<Boolean> = MutableLiveData(false)

    // load the album list
    fun loadAlbums() {
        viewModelScope.launch {
            getAlbumsUseCase(Unit).collect {
                when (it) {
                    is Result.Loading -> {
                        Timber.i("Albums data loading")
                    }
                    is Result.Error -> {
                        Timber.e("Error while loading albums")
                        getAlbumsError.postValue(true)
                    }

                    is Result.Success -> {
                        Timber.i("Albums successfully loaded")
                        it.data?.let { list ->
                            listAlbums.postValue(list)
                        }
                        getAlbumsError.postValue(false)
                    }
                }
            }
        }
    }
}
