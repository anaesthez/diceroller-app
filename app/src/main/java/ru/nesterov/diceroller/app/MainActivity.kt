package ru.nesterov.diceroller.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.nesterov.diceroller.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Repositories.init(applicationContext)
        super.onCreate(savedInstanceState)
        Timber.plant()
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
    }
}