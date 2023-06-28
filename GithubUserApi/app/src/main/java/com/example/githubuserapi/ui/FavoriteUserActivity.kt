package com.example.githubuserapi.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapi.data.local.FavoriteEntity
import com.example.githubuserapi.databinding.ActivityFavoritedUserBinding
import com.example.githubuserapi.recycleview.RecyclerViewData
import com.example.githubuserapi.ui.adapter.RecyclerViewAdapter
import com.example.githubuserapi.ui.viewmodel.UsersViewModel
import com.example.githubuserapi.ui.viewmodel.UsersViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritedUserBinding
    private val userViewModel: UsersViewModel by viewModels {
        UsersViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritedUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        userViewModel.getFavoriteUsers().observe(this) { users: List<FavoriteEntity>? ->
            val items = ArrayList<RecyclerViewData>()
            users?.map {
                val item = RecyclerViewData(login = it.userlogin, avatarUrl = it.avatarUser)
                items.add(item)
            }
            val adapter = RecyclerViewAdapter(items)
            binding.rvListUser.adapter = adapter
            val layoutManager = LinearLayoutManager(this)
            binding.rvListUser.layoutManager = layoutManager
            val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
            binding.rvListUser.addItemDecoration(itemDecoration)

            adapter.setOnItemClickCallback(object : RecyclerViewAdapter.OnItemClickCallback {
                override fun onItemClicked(data: RecyclerViewData) {
                    val detailIntent =
                        Intent(this@FavoriteUserActivity, DetailUserActivity::class.java)
                    detailIntent.putExtra(DetailUserActivity.DATA_LOGIN, data.login)
                    startActivity(detailIntent)
                }
            })
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}