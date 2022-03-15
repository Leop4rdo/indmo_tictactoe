package br.senai.sp.jandira.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.senai.sp.jandira.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** aviso de funcionalidade ainda em desenvolvimento */
        val dialogWIP = Toast.makeText(this, R.string.dialog_wip, Toast.LENGTH_SHORT)

        binding.buttonPve.setOnClickListener { openDificuldadeActivity() }
        binding.buttonPvp.setOnClickListener { startGame() }
    }

    private fun openDificuldadeActivity() {
        val intent = Intent(this, DificuldadeActivity::class.java)

        startActivity(intent)
    }

    private fun startGame() {
        val intent = Intent(this, GameActivity::class.java)

        intent.putExtra("gameMode", GameModes.PVP) // definindo modo de jogo para pvp

        startActivity(intent)
    }
}