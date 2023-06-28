package com.example.githubuserapi.di

import android.content.Context
import com.example.githubuserapi.data.local.UsersDatabase
import com.example.githubuserapi.data.remote.FavoriteRepository
import com.example.githubuserapi.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): FavoriteRepository {
        val database = UsersDatabase.getInstance(context)
        val dao = database.favoriteDao()
        val appExecutors = AppExecutors()
        return FavoriteRepository.getInstance(dao, appExecutors)
    }
}