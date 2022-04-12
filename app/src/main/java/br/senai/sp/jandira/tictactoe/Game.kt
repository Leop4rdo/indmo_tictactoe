package br.senai.sp.jandira.tictactoe

import java.lang.Exception

class Game {
    lateinit var mode: GameModes
    lateinit var difficulty: GameDifficulties

    /**
     * guarda o estado atual dos tiles do jogo
     *
     * 0 -> preenchido pelo jogador 1
     * 1 -> preenchido pelo jogador 2
     * 2 -> tile não preenchido ainda
     */
    var state = IntArray(9) { 2 }

    /** responsavel por determinar quantas jogadas ja foram realizadas */
    var turnCount = 0

    /** define se o jogo acabou */
    var isOver = false

    /**
     * define de quem é a vez.
     * 0 -> player 1,
     * 1 -> player 2 ou AI,
     */
    var activePlayer = 0

    /** guarda os tokens dos jogadores*/
    val tokens = arrayOf(R.drawable.ic_token_x, R.drawable.ic_token_o)

    fun move(tileIndex: Int) {
        state[tileIndex] = activePlayer

        turnCount++

        isOver = isGameOverByWin() || isGameOverByDraw()
    }

    /** retorna se o jogo acabou com empate */
    fun isGameOverByDraw(): Boolean {
        isOver = (turnCount >= 9)
        return isOver
    }

    /**
     * Verifica se o jogo acabou com uma vitoria de um dos jogadores
     */
    fun isGameOverByWin() : Boolean {
        isOver = isHorizontalWin() || isVerticalWin() || isDiagonalWin()
        return isOver
    }

    /**
     * Verifica se o jogo acabou com uma vitoria na horizontal
     */
    fun isHorizontalWin() : Boolean {
        return (state[0] != 2 && state[0] == state[1] && state[1] == state[2]) ||
                (state[3] != 2 && state[3] == state[4] && state[4] == state[5]) ||
                (state[6] != 2 && state[6] == state[7] && state[7] == state[8])
    }

    /**
     * Verifica se o jogo acabou com uma vitoria na vertical
     */
    fun isVerticalWin() : Boolean {
        return (state[0] != 2 && state[0] == state[3] && state[3] == state[6]) ||
                (state[1] != 2 && state[1] == state[4] && state[4] == state[7]) ||
                (state[2] != 2 && state[2] == state[5] && state[5] == state[8])
    }

    /**
     * Verifica se o jogo acabou com uma vitoria na diagonal
     */
    fun isDiagonalWin() : Boolean {
        return (state[0] != 2 && state[0] == state[4] && state[4] == state[8]) ||
                (state[2] != 2 && state[2] == state[4] && state[4] == state[6])

    }

    /** reinicia o jogo */
    fun restart() {
        state = IntArray(9) { 2 }
        isOver = false

        activePlayer= 0
        turnCount = 0
    }

    /** retorna o nome do jogadro passado como parametro */
    fun getPlayerName(player: Int) :  String{
        when (player){
            0 -> return "X"
            1 -> return "O"
            else -> throw Exception("Player Not Found")
        }
    }
}