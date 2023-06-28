package com.example.githubuserapi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserapi.R
import com.example.githubuserapi.data.local.FavoriteEntity
import com.example.githubuserapi.ui.adapter.SectionsPagerAdapter
import com.example.githubuserapi.databinding.ActivityDetailUserBinding
import com.example.githubuserapi.data.remote.response.DetailUserResponse
import com.example.githubuserapi.ui.viewmodel.UsersViewModel
import com.example.githubuserapi.ui.viewmodel.UsersViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class DetailUserActivity : AppCompatActivity() {
    private val userViewModel: UsersViewModel by viewModels {
        UsersViewModelFactory.getInstance(application)
    }
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var ivDetailUserAvatar: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var viewPager: ViewPager2
    private lateinit var tabs: TabLayout

    private lateinit var login: String
    private lateinit var avatarUser: String
    private lateinit var icFavorite: MenuItem
    private var status = false

    companion object {
        const val DATA_LOGIN = ""

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers_number,
            R.string.following_number
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ivDetailUserAvatar = findViewById(R.id.ivDetailUserAvatar)
        progressBar = findViewById(R.id.progressBar)
        viewPager = findViewById(R.id.viewPager)
        tabs = findViewById(R.id.tabLayout)

        val login: String = intent.getStringExtra(DATA_LOGIN).toString()

        userViewModel.onUser(login)
        userViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        userViewModel.detailUser.observe(this) {
            setDetailUser(it)
        }
        userViewModel.getFavoriteUser(login).observe(this) {
            status = it != null
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu_detail_user, menu)
        icFavorite = menu.findItem(R.id.favorite)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val user = FavoriteEntity(login, avatarUser)
        when (item.itemId) {
            R.id.favorite -> {
                if (!status) {
                    userViewModel.saveUser(user)
                    lifecycleScope.launch { icFavorite.setIcon(R.drawable.ic_favorited) }
                } else {
                    userViewModel.deleteUser(user.userlogin)
                    lifecycleScope.launch { icFavorite.setIcon(R.drawable.ic_favorite) }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setDetailUser(user: DetailUserResponse?) {
        lifecycleScope.launch {
            if (!status) {
                icFavorite.setIcon(R.drawable.ic_favorite)
            } else {
                icFavorite.setIcon(R.drawable.ic_favorited)
            }
        }

        user?.let {
            binding.tvUsername.text = it.name
            binding.tvUserLogin.text = it.login

            login = it.login.toString()
            avatarUser = it.avatarUrl.toString()
        }
        Glide.with(this)
            .load(avatarUser)
            .into(ivDetailUserAvatar)

        val sectionPagerAdapter = SectionsPagerAdapter(this, login)
        viewPager.adapter = sectionPagerAdapter

        TabLayoutMediator(tabs, viewPager) { tab, index ->
            tab.text = if (index == 0) {
                resources.getString(TAB_TITLES[index], user?.followers)
            } else {
                resources.getString(TAB_TITLES[index], user?.following)
            }
        }.attach()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}