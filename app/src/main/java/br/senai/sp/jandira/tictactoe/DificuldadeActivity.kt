package br.senai.sp.jandira.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.senai.sp.jandira.tictactoe.databinding.ActivityDificuldadeBinding

class DificuldadeActivity : AppCompatActivity() {
    lateinit var binding: ActivityDificuldadeBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDificuldadeBinding.inflate(layoutInflater);
        setContentView(binding.root)

        /** aviso de funcionalidade ainda em desenvolvimento */
        val dialogWIP = Toast.makeText(this, R.string.dialog_wip, Toast.LENGTH_SHORT)

        binding.buttonVoltar.setOnClickListener { finish() }

        binding.buttonFacil.setOnClickListener { startGame(GameDifficulties.EASY) }
        binding.buttonNormal.setOnClickListener { dialogWIP.show() }
        binding.buttonDificil.setOnClickListener { dialogWIP.show() }
    }

    private fun startGame(gameDificulty : GameDifficulties) {
        val intent = Intent(this, GameActivity::class.java)

        intent.putExtra("gameDificulty", gameDificulty)
        intent.putExtra("gameMode", GameModes.PVE) // definindo modo de jogo para pve

        startActivity(intent)
    }
}