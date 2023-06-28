package com.example.mybottomnavigation

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mybottomnavigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        //berisi kumpulan id yang ada di dalam menu BottomNavigation, khususnya yang ingin dikonfigurasi AppBar-nya supaya berbentuk Menu
        val appBarConfiguration = AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_profile
            ).build()
        setupActionBarWithNavController(navController, appBarConfiguration) //mengatur judul AppBar agar sesuai dengan Fragment yang ditampilkan
        navView.setupWithNavController(navController) //digunakan supaya Bottom Navigation menampilkan Fragment yang sesuai ketika menu dipilih
    }
}