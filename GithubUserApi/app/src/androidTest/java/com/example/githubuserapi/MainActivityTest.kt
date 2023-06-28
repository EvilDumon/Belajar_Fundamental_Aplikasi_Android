package com.example.githubuserapi

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.githubuserapi.ui.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {
    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun toFavoriteList(){
        onView(withId(R.id.favorite)).perform(click())
        onView(withId(R.id.rvListUser)).perform(click())
    }

    @Test
    fun changeTheme(){
        onView(withId(R.id.setting)).perform(click())
        onView(withId(R.id.switch_theme)).perform(click())
    }
}