package com.lbcalbums.ui.di

import android.content.Context
import com.lbcalbums.BuildConfig
import com.lbcalbums.data.NetworkUtils
import com.lbcalbums.data.datasource.AlbumRemoteDatasource
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
        cityDataSource: AlbumRemoteDatasource,
        networkUtils: NetworkUtils
    ): AlbumRepository {
        return AlbumRepositoryImpl(
            cityDataSource,
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
}
