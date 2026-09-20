package ru.netology.testing.uiautomator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.testing.uiautomator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            buttonChange.setOnClickListener {
                val input = userInput.text.toString()
                if (input.isNotBlank()) {
                    textToBeChanged.text = input
                }
            }

            buttonActivity.setOnClickListener {
                val input = userInput.text.toString()
                val intent = android.content.Intent(this@MainActivity, ShowTextActivity::class.java)
                intent.putExtra("testing_message", input)
                startActivity(intent)
            }
        }
    }
}
