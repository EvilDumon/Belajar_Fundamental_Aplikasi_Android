package com.example.githubuserapi.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favoriteUsers")
    fun getFavoriteUsers(): LiveData<List<FavoriteEntity>>

    @Query("SELECT * FROM favoriteUsers WHERE userLogin = :userLogin")
    fun getFavoriteUsers(userLogin: String): LiveData<FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: FavoriteEntity)

    @Query("DELETE FROM favoriteUsers WHERE userlogin = :userLogin")
    fun deleteUsers(userLogin: String)
}