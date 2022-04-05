package br.senai.sp.jandira.tictactoe

import kotlin.random.Random

class GameAI(gameDifficulty: GameDifficulties, game: Game){
    val gameDifficulty = gameDifficulty;
    val game = game;

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
        } while (game.state[move] != 2)

        return move
    }

    private fun hardMove() : Int {
        val bestMove = findBestMove();


        return bestMove
    }

    private fun findBestMove() : Int {
        if (game.isOver) return -1

        for (i in 0..)
    }

}