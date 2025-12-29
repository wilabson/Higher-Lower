package com.example.higherlower

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.higherlower.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // View binding.
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Starts the game when the play button is pressed.
        binding.btnPlay.setOnClickListener {
            handleGame()
        }

        // Opens the how to play activity when the "How to play" button is pressed.
        binding.btnHowtoplay.setOnClickListener {
            handleHowTo()
        }
    }

    // Starts the GameActivity
    fun handleGame() {
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    // Starts the HowToActivity
    fun handleHowTo() {
        val intent = Intent(this, HowToActivity::class.java)
        startActivity(intent)
    }
}