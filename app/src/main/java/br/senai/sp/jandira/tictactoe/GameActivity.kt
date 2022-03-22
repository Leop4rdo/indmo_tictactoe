package br.senai.sp.jandira.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import br.senai.sp.jandira.tictactoe.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {
    lateinit var binding: ActivityGameBinding

    val tokens = arrayOf(R.drawable.ic_token_x, R.drawable.ic_token_o)

    /** estado atual dos tiles */
    val gameState = IntArray(9) { 2 }

    /**
     * define de quem é a vez.
     * 0 -> player 1,
     * 1 -> player 2 ou AI,
     */
    var activePlayer = 0

    /** responsavel por determinar quantas jogadas ja foram realizadas */
    var turnCount = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** aviso de funcionalidade ainda em desenvolvimento */
        val dialogWIP = Toast.makeText(this, R.string.dialog_wip, Toast.LENGTH_SHORT)

        /** array responsavel por armazenar todos os tiles do board */
        val gameTiles = arrayOf<ImageView>(binding.tile0, binding.tile1, binding.tile2,
                                            binding.tile3, binding.tile4, binding.tile5,
                                            binding.tile6, binding.tile7, binding.tile8)

        // o bloco de codigo abaixo defini o ClickListener de cada tile para chamar a função onPLayerTap();
        gameTiles.forEach {
            it.setOnClickListener { onPlayerTap(it as ImageView) }
        }

        binding.buttonVoltar.setOnClickListener { finish() }
        binding.buttonHome.setOnClickListener { dialogWIP.show() }
    }

    /**
     * função chamada quando o jogador realiza um movimento
     */
    private fun onPlayerTap(tile: ImageView) {
        val tileId = tile.getTag().toString().toInt();

        // se o tile ja  estiver preenchido, impede a jogada e exibe uma mensagem
        if (gameState[tileId] != 2) return Toast.makeText(this, R.string.dialog_invalid_move, Toast.LENGTH_SHORT).show()

        // realizando a jogada
        tile.setImageResource(tokens[activePlayer]);
        gameState[tileId] = activePlayer;

        turnCount++

        // verifica se alguem ganhou
        if (isGameOverByWin()) return Toast.makeText(this, "Game Over, player ${activePlayer} ganhou!", Toast.LENGTH_SHORT).show()

        // verifica se deu velha
        if (turnCount >= 9) return Toast.makeText(this, "Game Over, ninguem ganhou!", Toast.LENGTH_SHORT).show()

        // alterando o jogador ativo
        updateActivePlayer()
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
}