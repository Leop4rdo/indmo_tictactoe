package br.senai.sp.jandira.tictactoe

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.Window

import android.widget.ImageView
import android.widget.Toast

import br.senai.sp.jandira.tictactoe.databinding.ActivityGameBinding
import br.senai.sp.jandira.tictactoe.databinding.GameOverDialogBinding

class GameActivity : AppCompatActivity() {
    lateinit var binding: ActivityGameBinding

    lateinit var gameMode: GameModes
    lateinit var gameDifficulty: GameDifficulties

    lateinit var gameTiles: Array<ImageView>

    val tokens = arrayOf(R.drawable.ic_token_x, R.drawable.ic_token_o)

    /** array responsavel por armazenar todos os tiles do board */


    /**
     * guarda o estado atual dos tiles do jogo
     *
     * 0 -> preenchido pelo jogador 1
     * 1 -> preenchido pelo jogador 2
     * 2 -> tile não preenchido ainda
     */
    var gameState = IntArray(9) { 2 }

    var gameOver = false

    /**
     * define de quem é a vez.
     * 0 -> player 1,
     * 1 -> player 2 ou AI,
     */
    var activePlayer = 0

    /** responsavel por determinar quantas jogadas ja foram realizadas */
    var turnCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gameMode = GameModes.valueOf(this.intent.getStringExtra("gameMode")!!);
        if ( gameMode == GameModes.PVE ) gameDifficulty = GameDifficulties.valueOf(this.intent.getStringExtra("gameDifficulty")!!);

        // atribuindo os tiles ao array gameTiles
        gameTiles = arrayOf<ImageView>(binding.tile0, binding.tile1, binding.tile2,
            binding.tile3, binding.tile4, binding.tile5,
            binding.tile6, binding.tile7, binding.tile8)


        // o bloco de codigo abaixo define o ClickListener de cada tile para chamar a função makeMove(tile);
        gameTiles.forEach {
            it.setOnClickListener { makeMove(it as ImageView) }
        }

        binding.buttonVoltar.setOnClickListener { finish() }
        binding.buttonHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
        }
    }

    /**
     * função chamada quando o jogador realiza um movimento
     */
    private fun makeMove(tile: ImageView) {
        // se o jogo ja terminou, impede a realização de novos movimentos
        if (gameOver) return

        // pegando o index do tile no array gameState[]
        val tileIndex = tile.getTag().toString().toInt();

        // se o tile ja  estiver preenchido, impede a jogada e exibe uma mensagem
        if (gameState[tileIndex] != 2) return Toast.makeText(this, R.string.dialog_invalid_move, Toast.LENGTH_SHORT).show()

        // realizando a jogada
        tile.setImageResource(tokens[activePlayer]);
        gameState[tileIndex] = activePlayer;

        turnCount++

        // verifica se alguem ganhou
        if (isGameOverByWin()) return showGameOverDialog("Player ${activePlayer + 1} Ganhou!!!")

        // verifica se deu velha
        if (turnCount >= 9) return showGameOverDialog("Empate!!!")

        // alterando o jogador ativo
        updateActivePlayer()

        if (gameMode == GameModes.PVE && activePlayer == 1) {
            val gameAI = GameAI(gameDifficulty, gameState);
            val aiMove = gameAI.makeMove()

            makeMove(gameTiles[aiMove]);
        }
    }

    /**
     * Verifica se o jogo acabou com uma vitoria de um dos jogadores
     */
    private fun isGameOverByWin() : Boolean {
        return  isHorizontalWin() || isVerticalWin() || isDiagonalWin()
    }

    /**
     * Verifica se o jogo acabou com uma vitoria na horizontal
     */
    private fun isHorizontalWin() : Boolean {
        return (gameState[0] != 2 && gameState[0] == gameState[1] && gameState[1] == gameState[2]) ||
                (gameState[3] != 2 && gameState[3] == gameState[4] && gameState[4] == gameState[5]) ||
                (gameState[6] != 2 && gameState[6] == gameState[7] && gameState[7] == gameState[8])
    }

    /**
     * Verifica se o jogo acabou com uma vitoria na vertical
     */
    private fun isVerticalWin() : Boolean {
        return (gameState[0] != 2 && gameState[0] == gameState[3] && gameState[3] == gameState[6]) ||
                (gameState[1] != 2 && gameState[1] == gameState[4] && gameState[4] == gameState[7]) ||
                (gameState[2] != 2 && gameState[2] == gameState[5] && gameState[5] == gameState[8])
    }

    /**
     * Verifica se o jogo acabou com uma vitoria na diagonal
     */
    private fun isDiagonalWin() : Boolean {
        return (gameState[0] != 2 && gameState[0] == gameState[4] && gameState[4] == gameState[8]) ||
                (gameState[2] != 2 && gameState[2] == gameState[4] && gameState[4] == gameState[6])

    }

    /**
     * define o jogador ativo e atualiza o indicador
     */
    private fun updateActivePlayer() {
        if (activePlayer == 0) {
            // Vez do jogador 2

            activePlayer = 1
            binding.imgCurrentTurn.setImageResource(R.drawable.ic_gray_o)
        } else {
            // Vez do jogador 1

            activePlayer = 0
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

        dialogBinding.dialogButtonHome.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java);

            startActivity(intent);
        }

        dialogBinding.dialogButtonRestart.setOnClickListener {
            restartGame()
            dialog.dismiss()
        }

        dialog.show()
    }

    /**
     * Responsavel por reiniciar o jogo e o board
     */
    private fun restartGame() {
        gameState = IntArray(9) { 2 }
        turnCount = 0;

        // limpando board
        val gameTiles = arrayOf<ImageView>(binding.tile0, binding.tile1, binding.tile2,
            binding.tile3, binding.tile4, binding.tile5,
            binding.tile6, binding.tile7, binding.tile8)

        gameTiles.forEach {
            it.setImageResource(0);
        }

        activePlayer = 0;
        binding.imgCurrentTurn.setImageResource(R.drawable.ic_gray_x)
    }
}