package br.com.vandodev.copa_2022_android.di

import android.content.Context
import androidx.room.Room
import br.com.vandodev.data.local.AppDatabase
import br.com.vandodev.data.local.TeamLocalDataSource
import br.com.vandodev.data.local.TeamLocalDataSourceImpl
import br.com.vandodev.data.remote.Copa2022Api
import br.com.vandodev.data.remote.TeamRemoteDataSource
import br.com.vandodev.data.remote.TeamRemoteDataSourceImpl
import br.com.vandodev.data.repository.MatchesRepositoryImpl
import br.com.vandodev.data.repository.NotificationRepositoryImpl
import br.com.vandodev.domain.repository.MatchesRepository
import br.com.vandodev.domain.repository.NotificationRepository
import br.com.vandodev.domain.repository.NotificationScheduler
import br.com.vandodev.warningscheduler.NotificationSchedulerImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(private val context: Context) {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // CORREÇÃO DEFINITIVA: Usando uma URL funcional da comunidade para a API.
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/natanfelipe/copa-2022-api/main/")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api: Copa2022Api = retrofit.create(Copa2022Api::class.java)

    private val db: AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "copa-2022-db"
    ).build()

    private val localDataSource: TeamLocalDataSource = TeamLocalDataSourceImpl(db.matchDao())
    private val remoteDataSource: TeamRemoteDataSource = TeamRemoteDataSourceImpl(api)
    
    private val notificationScheduler: NotificationScheduler = NotificationSchedulerImpl(context)

    val matchesRepository: MatchesRepository = MatchesRepositoryImpl(context)
    val notificationRepository: NotificationRepository = NotificationRepositoryImpl(localDataSource, notificationScheduler)
}
