package com.example.higherlower

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.higherlower.databinding.ActivityGameBinding
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    // View binding.
    private lateinit var binding: ActivityGameBinding

    // Prevents the user from pressing or spamming buttons during animations.
    private var isGameLogicRunning = false

    // Card values 0-12
    private var currentCard = 0
    private var nextCard = 0

    // Player score and high score.
    private var score = 0
    private var highScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the high score from shared preferences.
        val prefs = getSharedPreferences("highscore_prefs", MODE_PRIVATE)
        highScore = prefs.getInt("HIGH_SCORE", 0)
        binding.tvHighScore.text = "High Score: $highScore"

        // Set the initial card values.
        binding.ivCurrentcard.setImageResource(R.drawable.cardselectorlevellist)
        binding.ivGuessedcard.setImageResource(R.drawable.cardselectorlevellist)

        // Show card back for the guessed card.
        binding.ivGuessedcard.setImageLevel(14)

        // Generate and display the starting card.
        currentCard = Random.nextInt(0, 13)
        binding.ivCurrentcard.setImageLevel(currentCard)

        // Back button to return to main menu.
        binding.btnImageBack.setOnClickListener {
            finish()
        }

        // Guess button to guess higher.
        binding.btnHigher.setOnClickListener {
            guessCard(isHigher = true)
        }
        // Guess button to guess lower.
        binding.btnLower.setOnClickListener {
            guessCard(isHigher = false)
        }
    }

    // Logic for when the player guesses a card.
    private fun guessCard(isHigher: Boolean) {

        // Prevents the user from spamming buttons during animations.
        if (isGameLogicRunning) return
        isGameLogicRunning = true

        // Generates the next card.
        generateNextCard()

        // Flips card to reveal guessed card animation.
        cardFlipAnim(binding.ivGuessedcard, nextCard) {
            // Delay
            Handler(Looper.getMainLooper()).postDelayed({
                // Flips card back to show card back animation.
                cardFlipAnim(binding.ivGuessedcard, 14) {

                    // Logic to check if the guess is correct.
                    val isCorrect = if (isHigher) {
                        nextCard >= currentCard
                    } else {
                        nextCard <= currentCard
                    }
                    // If the guess is wrong the game is over.
                    if (!isCorrect) {
                        goToGameOver()
                    } else {
                        // If the guess is correct the score is increased.
                        score++
                        binding.tvScoreText.text = "Score: $score"

                        // Updates and saves the high score if the current score is higher.
                        if (score > highScore) {
                            highScore = score
                            val prefs = getSharedPreferences("highscore_prefs", MODE_PRIVATE)
                            prefs.edit().putInt("HIGH_SCORE", highScore).apply()
                            binding.tvHighScore.text = "High Score: $highScore"
                        }

                        // Sets the current card to the next card.
                        currentCard = nextCard
                        cardFlipAnim(binding.ivCurrentcard, currentCard)

                        // Allows the user to press the buttons again.
                        isGameLogicRunning = false
                    }
                }
            }, 400)
        }
    }

    // Generates a random card for the next guess.
    fun generateNextCard() {
        nextCard = Random.nextInt(0, 13)
        binding.ivGuessedcard.setImageLevel(nextCard)
    }

    // Opens the game over activity and passes the score and high score.
    private fun goToGameOver() {
        val intent = Intent(this, GameOverActivity::class.java)
        intent.putExtra("SCORE", score)
        intent.putExtra("HIGH_SCORE", highScore)
        startActivity(intent)
        finish()
    }

    // Card flip animation.
    private fun cardFlipAnim(imageView: ImageView, newLevel: Int, onComplete: (() -> Unit)? = null) {

        // Shrinks the card horizontally.
        imageView.animate()
            .scaleX(0f)
            .setDuration(150)
            .withEndAction {
                // Change the face of the card while animating.
                imageView.setImageLevel(newLevel)

                // Expands the card horizontally to normal size.
                imageView.animate()
                    .scaleX(1f)
                    .setDuration(150)
                    .withEndAction {
                        onComplete?.invoke()
            }.start()
        }.start()
    }
}