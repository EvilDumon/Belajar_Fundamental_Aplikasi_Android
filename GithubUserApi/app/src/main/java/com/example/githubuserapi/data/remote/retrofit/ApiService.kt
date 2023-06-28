package com.example.githubuserapi.data.remote.retrofit

import com.example.githubuserapi.data.remote.response.DetailUserResponse
import com.example.githubuserapi.data.remote.response.ItemsItem
import com.example.githubuserapi.data.remote.response.SearchResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("users")
    fun getUsersWithoutUsername(): Call<List<ItemsItem>>

    @GET("search/users")
    fun getSearchedUsername(@Query("q") username: String): Call<SearchResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}