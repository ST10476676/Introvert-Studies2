package vcmsa.ci.introvertstudies

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {

    private val questions = listOf(
        "Was the Declaration of Independence signed in 1776?" to true,
        "Did Libya gain independence from Italy in 1990?" to false,
        "Pluto was downgraded to a dwarf planet in 2006." to true,
        "Barack Obama won the 2008 U.S. Presidential election." to true,
        "The word 'Ubuntu' means justice for all." to false
    )

    private var currentQuestionIndex = 0
    private var score = 0
    private val reviewList = mutableListOf<String>()

    private lateinit var questionTextView: TextView
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var scoreButton: Button
    private lateinit var feedbackTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        questionTextView = findViewById(R.id.question_text)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        scoreButton = findViewById(R.id.scoreButton)
        feedbackTextView = findViewById(R.id.feedback_text)

        scoreButton.visibility = View.GONE
        feedbackTextView.visibility = View.GONE

        showQuestion()

        trueButton.setOnClickListener { checkAnswer(true) }
        falseButton.setOnClickListener { checkAnswer(false) }

        scoreButton.setOnClickListener {
            val intent = Intent(this, ScoreActivity::class.java)
            intent.putExtra("score", score)
            intent.putExtra("total", questions.size)
            intent.putStringArrayListExtra("review", ArrayList(reviewList))
            startActivity(intent)
            finish()
        }
    }

    private fun showQuestion() {
        if (currentQuestionIndex < questions.size) {
            questionTextView.text = questions[currentQuestionIndex].first
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val (question, correctAnswer) = questions[currentQuestionIndex]

        if (userAnswer == correctAnswer) {
            score++
            feedbackTextView.text = "Correct!"
            feedbackTextView.setTextColor(getColor(android.R.color.holo_green_dark))
            reviewList.add("Q${currentQuestionIndex + 1}: \"$question\" - ✅ Correct")
        } else {
            feedbackTextView.text = "Wrong!"
            feedbackTextView.setTextColor(getColor(android.R.color.holo_red_dark))
            reviewList.add("Q${currentQuestionIndex + 1}: \"$question\" - ❌ Incorrect")
        }

        feedbackTextView.visibility = View.VISIBLE
        currentQuestionIndex++

        if (currentQuestionIndex < questions.size) {
            Handler(Looper.getMainLooper()).postDelayed({
                feedbackTextView.visibility = View.GONE
                showQuestion()
            }, 1000)
        } else {
            trueButton.visibility = View.GONE
            falseButton.visibility = View.GONE
            questionTextView.text = "Quiz completed!"
            scoreButton.visibility = View.VISIBLE
        }
    }
}