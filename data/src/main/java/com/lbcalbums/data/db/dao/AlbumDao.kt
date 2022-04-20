package com.lbcalbums.data.db.dao

import androidx.room.*
import com.lbcalbums.data.db.entity.Album

/**
 * [Album] DAO
 */
@Dao
interface AlbumDao {

    @Query("SELECT * FROM album")
    fun getAll(): List<Album>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: Album)

    @Delete
    fun delete(action: Album)

}