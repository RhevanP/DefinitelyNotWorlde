package com.example.definitelynotworlde.GameManager

class gameManager {
    companion object{
        var gameState = 0 //1 for win, -1 for loosing and 0 for keep going
        var maxTries = 5
        var triesDone = 0

        fun tryRecording(){
            triesDone++
        }

        fun livesLeft(): Boolean{
            return triesDone == maxTries
        }

        fun gameOverLoose(){
            gameState = -1
        }

        fun gameOverWin(){
            gameState = 1
        }

        fun resetGame(){
            gameState = 0
            maxTries = 5
            triesDone = 0
        }
    }
}