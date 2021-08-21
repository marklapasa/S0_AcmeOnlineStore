package com.example.acmeonlinestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.acmeonlinestore.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)
    supportActionBar?.elevation = 0f
    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
        .replace(R.id.container, MainFragment.newInstance())
        .commitNow()
    }
  }
}
