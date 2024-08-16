package com.kripto.pruebakripto.di

import android.content.Context
import android.content.pm.PackageManager
import androidx.room.Room
import com.kripto.pruebakripto.data.database.AppDatabase
import com.kripto.pruebakripto.data.database.dao.AppInfoDao
import com.kripto.pruebakripto.data.repository.AppRepository
import com.kripto.pruebakripto.data.repository.AppRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "prueba_kripto_database"
        ).build()
    }
    @Provides
    @Singleton
    fun provideAppInfoDao(appDatabase: AppDatabase) : AppInfoDao{
        return appDatabase.appInfoDao()
    }
    @Provides
    fun providePackageManager(@ApplicationContext context: Context): PackageManager {
        return context.packageManager
    }

    @Provides
    fun provideAppRepository(appInfoDao: AppInfoDao, packageManager: PackageManager, context: Context): AppRepository {
        return AppRepositoryImpl(appInfoDao, packageManager, context)
    }

}

@Module
@InstallIn(SingletonComponent::class)
object ContextModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }
}