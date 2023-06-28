package com.example.githubuserapi.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserapi.data.remote.FavoriteRepository
import com.example.githubuserapi.di.Injection

class UsersViewModelFactory private constructor(private val favoriteRepository: FavoriteRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
            return UsersViewModel(favoriteRepository) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: UsersViewModelFactory? = null
        fun getInstance(context: Context): UsersViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: UsersViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}