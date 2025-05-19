package vcmsa.ci.introvertstudies

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val scoreTextView: TextView = findViewById(R.id.scoreTextView)
        val motivationTextView: TextView = findViewById(R.id.motivationTextView)
        val reviewTextView: TextView = findViewById(R.id.reviewTextView)
        val restartButton: Button = findViewById(R.id.restartButton)

        val score = intent.getIntExtra("score", 0)
        val total = intent.getIntExtra("total", 0)
        val reviewList = intent.getStringArrayListExtra("review")

        scoreTextView.text = "Your score: $score / $total"

        motivationTextView.text = if (score < 5) {
            "Don't worry, keep practicing and you'll get better!"
        } else {
            "Well done! You did a great job!"
        }

        reviewTextView.text = reviewList?.joinToString("\n") ?: "No review available."

        restartButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}