package br.senai.sp.jandira.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import br.senai.sp.jandira.tictactoe.databinding.ActivityDificuldadeBinding

class DificuldadeActivity : AppCompatActivity() {
    lateinit var binding: ActivityDificuldadeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDificuldadeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** aviso de funcionalidade ainda em desenvolvimento */
        val dialogWIP = Toast.makeText(this, R.string.dialog_wip, Toast.LENGTH_SHORT)

        var fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        binding.buttonFacil.startAnimation(fadeInAnimation)
        binding.buttonNormal.startAnimation(fadeInAnimation)
        binding.buttonDificil.startAnimation(fadeInAnimation)
        binding.buttonVoltar.startAnimation(fadeInAnimation)

        binding.buttonVoltar.setOnClickListener { finish() }

        binding.buttonFacil.setOnClickListener { startGame(GameDifficulties.EASY) }
        binding.buttonNormal.setOnClickListener { dialogWIP.show() }
        binding.buttonDificil.setOnClickListener { dialogWIP.show() }
    }

    private fun startGame(gameDifficulty : GameDifficulties) {
        val intent = Intent(this, GameActivity::class.java)

        intent.putExtra("gameDifficulty", gameDifficulty.toString())
        intent.putExtra("gameMode", GameModes.PVE.toString()) // definindo modo de jogo para pve

        startActivity(intent)
    }
}