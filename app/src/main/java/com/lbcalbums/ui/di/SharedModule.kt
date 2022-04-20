package com.lbcalbums.ui.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.lbcalbums.BuildConfig
import com.lbcalbums.data.NetworkUtils
import com.lbcalbums.data.datasource.AlbumLocalDatasource
import com.lbcalbums.data.datasource.AlbumRemoteDatasource
import com.lbcalbums.data.db.AppDatabase
import com.lbcalbums.data.db.dao.AlbumDao
import com.lbcalbums.data.network.AlbumService
import com.lbcalbums.data.repository.AlbumRepositoryImpl
import com.lbcalbums.domain.repository.AlbumRepository
import com.lbcalbums.ui.utils.NetworkUtilsImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class SharedModule {

    @Singleton
    @Provides
    fun provideNetworkUtils(
        @ApplicationContext context: Context
    ): NetworkUtils {
        return NetworkUtilsImpl(context)
    }

    @Singleton
    @Provides
    fun provideAlbumRepository(
        albumRemoteDataSource: AlbumRemoteDatasource,
        albumLocalDataSource: AlbumLocalDatasource,
        networkUtils: NetworkUtils
    ): AlbumRepository {
        return AlbumRepositoryImpl(
            albumRemoteDataSource,
            albumLocalDataSource,
            networkUtils
        )
    }

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesAlbumApi(retrofit: Retrofit): AlbumService = retrofit.create(
        AlbumService::class.java
    )

    @Provides
    @Singleton
    fun provideAppDatabase(
        app: Application
    ): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideAlbumDao(database: AppDatabase): AlbumDao = database.albumDao()
}
