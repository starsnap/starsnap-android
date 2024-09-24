package com.photo.android.di

import com.photo.android.network.auth.AuthApiRepositoryImpl
import com.photo.android.network.auth.AuthRepository
import com.photo.android.network.report.ReportApiRepositoryImpl
import com.photo.android.network.report.ReportRepository
import com.photo.android.network.snap.SnapApiRepositoryImpl
import com.photo.android.network.snap.SnapRepository
import com.photo.android.network.user.UserApiRepositoryImpl
import com.photo.android.network.user.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideAuthRepository(authApiRepositoryImpl: AuthApiRepositoryImpl): AuthRepository =
        authApiRepositoryImpl

    @Provides
    @Singleton
    fun provideUserRepository(userApiRepositoryImpl: UserApiRepositoryImpl): UserRepository =
        userApiRepositoryImpl

    @Provides
    @Singleton
    fun provideSnapRepository(snapApiRepositoryImpl: SnapApiRepositoryImpl): SnapRepository =
        snapApiRepositoryImpl

    @Provides
    @Singleton
    fun provideReportRepository(reportApiRepositoryImpl: ReportApiRepositoryImpl): ReportRepository =
        reportApiRepositoryImpl
}