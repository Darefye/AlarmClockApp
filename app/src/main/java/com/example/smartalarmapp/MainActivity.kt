package com.example.smartalarmapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.smartalarmapp.databinding.ActivityMainBinding
import com.example.smartalarmapp.fragments.MainFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance()).commit()
    }
}