package com.example.higherlower

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.higherlower.databinding.ActivityHowToBinding

class HowToActivity : AppCompatActivity() {

    // View binding
    private lateinit var binding: ActivityHowToBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHowToBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Back button to return to main menu.
        binding.btnImageBack.setOnClickListener {
            finish()
        }

        // Set how to play text.
        binding.tvHowtoRulesTitle.text = "How to Play"
        binding.tvHowtoRules1.text = "A card will be shown and your job is to guess if the next " +
                "card is higher or lower than the card shown, by pressing the corresponding button." +
                "\n\nThe card shown will be replaced by the next card. \n\nIf your guess is wrong you lose.\n"


    }
}