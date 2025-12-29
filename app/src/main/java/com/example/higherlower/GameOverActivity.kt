package com.example.higherlower

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.higherlower.databinding.ActivityGameOverBinding

class GameOverActivity : AppCompatActivity() {

    // View binding.
    private lateinit var binding: ActivityGameOverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameOverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the score and high score from the intent.
        val score = intent.getIntExtra("SCORE", 0)
        val highScore = intent.getIntExtra("HIGH_SCORE", 0)

        // Set the final score and high score to be displayed.
        binding.tvFinalScore.text = "Score: $score"
        binding.tvHighScore.text = "High Score: $highScore"

        // Animate the game over screen.
        animateGameOverScreen()

        // Restart button to restart the game.
        binding.btnRestart.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Main menu button to return to the main menu.
        binding.btnMainMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Animation to fade in the game over screen.
    private fun animateGameOverScreen() {
        binding.tvGameOver.animate().alpha(1f).setDuration(400).withEndAction {
            binding.tvFinalScore.animate().alpha(1f).setDuration(400).withEndAction {
                binding.tvHighScore.animate().alpha(1f).setDuration(400).withEndAction {
                    binding.buttonsLayout.animate().alpha(1f).setDuration(400).start()
                }.start()
            }.start()
        }.start()
    }
}
