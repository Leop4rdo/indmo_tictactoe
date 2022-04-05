package br.senai.sp.jandira.tictactoe

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

import android.view.Window

import android.widget.ImageView
import android.widget.Toast

import br.senai.sp.jandira.tictactoe.databinding.ActivityGameBinding
import br.senai.sp.jandira.tictactoe.databinding.GameOverDialogBinding
import java.lang.Exception

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding

    private val game = Game();

    /** array responsavel por armazenar todos os tiles do board */
    private lateinit var gameTiles: Array<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        game.mode = GameModes.valueOf(this.intent.getStringExtra("gameMode")!!);
        if ( game.mode == GameModes.PVE ) game.difficulty = GameDifficulties.valueOf(this.intent.getStringExtra("gameDifficulty")!!);

        // atribuindo os tiles ao array gameTiles
        gameTiles = arrayOf<ImageView>(binding.tile0, binding.tile1, binding.tile2,
            binding.tile3, binding.tile4, binding.tile5,
            binding.tile6, binding.tile7, binding.tile8)


        // o bloco de codigo abaixo define o ClickListener de cada tile para chamar a função makeMove(tile);
        gameTiles.forEach {
            it.setOnClickListener { makeMove(it as ImageView) }
        }

        binding.buttonVoltar.setOnClickListener { finish() }
        binding.buttonHome.setOnClickListener { goHome() }
    }

    private fun goHome() {
        val intent = Intent(this, MainActivity::class.java);

        startActivity(intent);
        finish()
    }

    /**
     * função chamada quando o jogador realiza um movimento
     */
    private fun makeMove(tile: ImageView) {
        // se o jogo ja terminou, impede a realização de novos movimentos
        if (game.isOver) return

        // pegando o index do tile no array gameState[]
        val tileIndex = tile.tag.toString().toInt();

        // se o tile ja  estiver preenchido, impede a jogada e exibe uma mensagem
        if (game.state[tileIndex] != 2) return Toast.makeText(this, R.string.dialog_invalid_move, Toast.LENGTH_SHORT).show()

        // realizando a jogada
        tile.setImageResource(game.tokens[game.activePlayer]);
        game.move(tileIndex);

        // verifica se alguem ganhou
        if (game.isGameOverByWin()) return showGameOverDialog("Player ${game.getPlayerName(game.activePlayer)} Ganhou!!!")

        // verifica se deu velha
        if (game.isGameOverByDraw()) return showGameOverDialog("Empate!!!")

        // alterando o jogador ativo
        updateActivePlayer()

        if (game.mode == GameModes.PVE && game.activePlayer == 1) {
            val gameAI = GameAI(game.difficulty, game);
            val aiMove = gameAI.makeMove()

            Handler().postDelayed({
                makeMove(gameTiles[aiMove]);
            }, 100)
        }
    }


    /**
     * define o jogador ativo e atualiza o indicador
     */
    private fun updateActivePlayer() {
        if (game.activePlayer == 0) {
            // Vez do jogador 2

            game.activePlayer = 1
            binding.imgCurrentTurn.setImageResource(R.drawable.ic_gray_o)
        } else {
            // Vez do jogador 1

            game.activePlayer = 0
            binding.imgCurrentTurn.setImageResource(R.drawable.ic_gray_x)
        }
    }

    /**
     * Exibe a mensagem de fim de jogo
     *
     * @param   message   messagem exibida no titulo do dialog
     */
    private fun showGameOverDialog(message: String) {
        val dialog: Dialog = Dialog(this);
        val dialogBinding: GameOverDialogBinding =  GameOverDialogBinding.inflate(layoutInflater)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawableResource(R.drawable.game_over_dialog);
        dialog.setContentView(dialogBinding.root);


        dialogBinding.dialogTitle.text = message;

        dialogBinding.dialogButtonHome.setOnClickListener { goHome() }

        dialogBinding.dialogButtonRestart.setOnClickListener {
            restartGame()
            dialog.dismiss()
        }

        dialog.show()
    }

    /**
     * Responsavel por reiniciar o jogo
     */
    private fun restartGame() {
        game.restart()

        // limpando board
        val gameTiles = arrayOf<ImageView>(binding.tile0, binding.tile1, binding.tile2,
            binding.tile3, binding.tile4, binding.tile5,
            binding.tile6, binding.tile7, binding.tile8)

        gameTiles.forEach {
            it.setImageResource(0);
        }

        binding.imgCurrentTurn.setImageResource(R.drawable.ic_gray_x)
    }


}