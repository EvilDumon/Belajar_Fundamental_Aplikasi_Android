package com.example.githubuserapi.data.remote

import androidx.lifecycle.LiveData
import com.example.githubuserapi.data.local.FavoriteDao
import com.example.githubuserapi.data.local.FavoriteEntity
import com.example.githubuserapi.utils.AppExecutors

class FavoriteRepository private constructor(
    private val usersDao: FavoriteDao,
    private val appExecutors: AppExecutors
) {
    fun getFavoriteUsers(): LiveData<List<FavoriteEntity>> {
        return usersDao.getFavoriteUsers()
    }

    fun getFavoriteUser(user: String): LiveData<FavoriteEntity> {
        return usersDao.getFavoriteUsers(user)
    }

    fun insertFavoriteUser(users: FavoriteEntity) {
        appExecutors.diskIO.execute {
            usersDao.insertUser(users)
        }
    }

    fun deleteFavoriteUser(user: String) {
        appExecutors.diskIO.execute {
            usersDao.deleteUsers(user)
        }
    }

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            usersDao: FavoriteDao,
            appExecutors: AppExecutors
        ): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(usersDao, appExecutors)
            }.also { instance = it }
    }
}