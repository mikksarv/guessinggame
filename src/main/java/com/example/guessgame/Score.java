package com.example.guessgame;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Score {
    public int score;
    public String playerName;
    public String difficulty;
    public String difficultyName;

    @Override
    public String toString() {
        return this.playerName + " Difficulty: " + this.difficultyName + " Score: " + this.score + "\n";
    }
}
