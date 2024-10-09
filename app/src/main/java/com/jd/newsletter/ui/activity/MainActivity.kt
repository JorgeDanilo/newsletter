package com.jd.newsletter.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.jd.newsletter.R
import com.jd.newsletter.ui.fragment.NewsletterFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("MainActivity", "onCreate()")
        if (savedInstanceState == null) {
            addFragment()
        }
    }

    private fun addFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<NewsletterFragment>(R.id.fragment_container_view)
        }
    }
}