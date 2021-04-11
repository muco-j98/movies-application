package com.example.movies_application.dependency

import android.content.Context
import androidx.room.Room
import com.example.movies_application.db.MoviesDatabase
import com.example.movies_application.network.api.ApiHelper
import com.example.movies_application.network.api.ApiHelperImpl
import com.example.movies_application.network.api.MovieApi
import com.example.movies_application.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule  {

    @Provides
    @Singleton
    fun provideMoviesDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        MoviesDatabase::class.java,
        "movies_db"
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideMovieDao(db: MoviesDatabase) = db.getMoviesDao()

    @Singleton
    @Provides
    fun provideOkHttpClient() = run {
        val loggingInterceptor = HttpLoggingInterceptor()
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL:String) =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideMovieApi(retrofit: Retrofit) = retrofit.create(MovieApi::class.java)

    @Singleton
    @Provides
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper
}