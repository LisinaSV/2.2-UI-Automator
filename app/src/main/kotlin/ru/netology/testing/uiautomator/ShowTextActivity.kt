package ru.netology.testing.uiautomator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.testing.uiautomator.databinding.ActivityShowTextBinding

class ShowTextActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityShowTextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val message = intent.getStringExtra("testing_message") ?: ""
        binding.text.text = message
    }
}
