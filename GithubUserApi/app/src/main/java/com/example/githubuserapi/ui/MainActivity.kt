package com.example.githubuserapi.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapi.ui.viewmodel.MainViewModel
import com.example.githubuserapi.R
import com.example.githubuserapi.SettingPreferences
import com.example.githubuserapi.ui.adapter.RecyclerViewAdapter
import com.example.githubuserapi.databinding.ActivityMainBinding
import com.example.githubuserapi.recycleview.RecyclerViewData
import com.example.githubuserapi.data.remote.response.ItemsItem
import com.example.githubuserapi.ui.viewmodel.ThemeViewModel
import com.example.githubuserapi.ui.viewmodel.ThemeViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private val a = ArrayList<RecyclerViewData>()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(username: String): Boolean {
                mainViewModel.showUsers(username)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(username: String): Boolean {
                if (username.isBlank()) {
                    mainViewModel.showUsers()
                }
                return true
            }
        })

        val favoriteView = menu.findItem(R.id.favorite)
        favoriteView.setOnMenuItemClickListener {
            val favoriteIntent = Intent(this@MainActivity, FavoriteUserActivity::class.java)
            startActivity(favoriteIntent)
            true
        }

        val settingView = menu.findItem(R.id.setting)
        settingView.setOnMenuItemClickListener {
            val settingIntent = Intent(this@MainActivity, SettingActivity::class.java)
            startActivity(settingIntent)
            true
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        mainViewModel.listUsers.observe(this) {
            showUsers(it)
        }

        mainViewModel.showUsers()

        val pref = SettingPreferences.getInstance(dataStore)
        val themeViewModel =
            ViewModelProvider(this, ThemeViewModelFactory(pref))[ThemeViewModel::class.java]

        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun showUsers(datas: List<ItemsItem>?) {
        a.clear()
        datas?.map {
            val data = RecyclerViewData(it.avatarUrl, it.login)
            a.add(data)
        }
        val adapter = RecyclerViewAdapter(a)
        binding.rvListUser.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        binding.rvListUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvListUser.addItemDecoration(itemDecoration)

        adapter.setOnItemClickCallback(object : RecyclerViewAdapter.OnItemClickCallback {
            override fun onItemClicked(data: RecyclerViewData) {
                val detailIntent = Intent(this@MainActivity, DetailUserActivity::class.java)
                detailIntent.putExtra(DetailUserActivity.DATA_LOGIN, data.login)
                startActivity(detailIntent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}