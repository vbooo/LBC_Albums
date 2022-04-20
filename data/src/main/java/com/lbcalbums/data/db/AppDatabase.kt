package com.lbcalbums.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lbcalbums.data.db.dao.AlbumDao
import com.lbcalbums.data.db.entity.Album


@Database(entities = [Album::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
}