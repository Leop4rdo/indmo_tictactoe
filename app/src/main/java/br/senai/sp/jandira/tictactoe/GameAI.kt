package br.senai.sp.jandira.tictactoe

import kotlin.random.Random

class GameAI(gameDifficulty: GameDifficulties, gameState: IntArray){
    val gameDifficulty = gameDifficulty;
    val gameState = gameState;

    fun makeMove() : Int {
        when (gameDifficulty) {
            GameDifficulties.EASY -> return makeAnEasyMove();
            GameDifficulties.NORMAL -> return 0;
            GameDifficulties.HARD -> return 0;
        }
    }

    private fun makeAnEasyMove() : Int {
        var move: Int

        do {
            move = Random.nextInt(until = 8);
        } while (gameState[move] != 2)

        return move
    }

}