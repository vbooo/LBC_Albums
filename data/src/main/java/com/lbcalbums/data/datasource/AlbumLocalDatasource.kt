package com.lbcalbums.data.datasource

import com.lbcalbums.data.db.dao.AlbumDao
import com.lbcalbums.data.db.entity.Album
import javax.inject.Inject

class AlbumLocalDatasource @Inject constructor(val albumDao: AlbumDao) {

    fun insertAlbums(list: List<Album>) {
        for (item in list) {
            albumDao.insert(item)
        }
    }

    fun getAll(): List<Album> {
        return albumDao.getAll()
    }

    fun deleteAll() {
        return albumDao.deleteAll()
    }
}
