package br.senai.sp.jandira.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val playerVsComputer : View = findViewById(R.id.pve)

        playerVsComputer.setOnClickListener { openDificuldadeActivity() }
    }

    private fun openDificuldadeActivity() {
        val intent = Intent(this, DificuldadeActivity::class.java)

        startActivity(intent)
    }
}