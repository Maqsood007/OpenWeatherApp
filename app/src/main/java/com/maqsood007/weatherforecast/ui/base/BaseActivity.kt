package com.test.nyt_most_viewed.ui.base

/**
 * @author Muhammad Maqsood.
 */


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.test.nyt_most_viewed.di.ViewModelFactory
import dagger.android.AndroidInjection
import javax.inject.Inject

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
    }
}