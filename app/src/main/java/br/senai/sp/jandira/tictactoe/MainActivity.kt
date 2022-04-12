package br.senai.sp.jandira.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import br.senai.sp.jandira.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        binding.buttonPve.startAnimation(fadeInAnimation)
        binding.buttonPvp.startAnimation(fadeInAnimation)

        binding.buttonPve.setOnClickListener { openDificuldadeActivity() }
        binding.buttonPvp.setOnClickListener { startGame() }
    }

    private fun openDificuldadeActivity() {
        val intent = Intent(this, DificuldadeActivity::class.java)

        startActivity(intent)
    }

    private fun startGame() {
        val intent = Intent(this, GameActivity::class.java)

        intent.putExtra("gameMode", GameModes.PVP.toString()) // definindo modo de jogo para pvp

        startActivity(intent)
    }
}