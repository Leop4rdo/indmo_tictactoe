package br.senai.sp.jandira.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.senai.sp.jandira.tictactoe.databinding.ActivityDificuldadeBinding
import br.senai.sp.jandira.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        binding.buttonPve.setOnClickListener { openDificuldadeActivity() }
    }

    private fun openDificuldadeActivity() {
        val intent = Intent(this, DificuldadeActivity::class.java)

        startActivity(intent)
    }
}