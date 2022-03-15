package br.senai.sp.jandira.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.senai.sp.jandira.tictactoe.databinding.ActivityDificuldadeBinding

class DificuldadeActivity : AppCompatActivity() {
    lateinit var binding: ActivityDificuldadeBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDificuldadeBinding.inflate(layoutInflater);
        setContentView(binding.root)

        // val voltar : ImageButton = findViewById(R.id.voltar)

        binding.buttonVoltar.setOnClickListener { finish() }
    }
}