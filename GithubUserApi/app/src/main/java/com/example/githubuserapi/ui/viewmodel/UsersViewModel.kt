package com.example.githubuserapi.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapi.data.local.FavoriteEntity
import com.example.githubuserapi.data.remote.FavoriteRepository
import com.example.githubuserapi.data.remote.response.DetailUserResponse
import com.example.githubuserapi.data.remote.response.ItemsItem
import com.example.githubuserapi.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersViewModel(private val usersRepository: FavoriteRepository) : ViewModel() {
    companion object {
        private const val TAG = "UsersViewModel"
    }

    private val _detailUser = MutableLiveData<DetailUserResponse?>()
    val detailUser: LiveData<DetailUserResponse?> = _detailUser

    private val _listFollowers = MutableLiveData<List<ItemsItem>>()
    val listFollowers: LiveData<List<ItemsItem>> = _listFollowers

    private val _listFollowing = MutableLiveData<List<ItemsItem>>()
    val listFollowing: LiveData<List<ItemsItem>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFavoriteUsers() = usersRepository.getFavoriteUsers()

    fun getFavoriteUser(user: String) = usersRepository.getFavoriteUser(user)

    fun saveUser(users: FavoriteEntity) = usersRepository.insertFavoriteUser(users)

    fun deleteUser(user: String) = usersRepository.deleteFavoriteUser(user)

    fun onUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun showFollowers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)

        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollowers.value = response.body()
                } else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun showFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)

        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollowing.value = response.body()
                } else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}