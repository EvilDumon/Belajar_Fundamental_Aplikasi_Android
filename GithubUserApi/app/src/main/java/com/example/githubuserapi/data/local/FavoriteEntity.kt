package com.example.githubuserapi.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favoriteUsers")
@Parcelize
class FavoriteEntity(
    @ColumnInfo(name = "userLogin")
    @PrimaryKey(autoGenerate = false)
    val userlogin: String,

    @ColumnInfo(name = "avatarUser")
    val avatarUser: String
) : Parcelable