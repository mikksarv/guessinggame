package com.example.guessgame;
import java.util.Random;
import java.util.UUID;
import lombok.Getter;

@Getter
public class Game {
    private UUID gameId = UUID.randomUUID();
    private int targetNumber;
    private int attemptsMade;
    private int maxAttempts;
    private boolean isWon;
    private boolean isOver;
    private int rangeMin;
    private int rangeMax;
    private String playerName;
    private int score;

    public Game(int maxAttempts, int rangeMin, int rangeMax, String playerName) {
        this.maxAttempts = maxAttempts;
        this.rangeMin = rangeMin;
        this.rangeMax = rangeMax;
        this.playerName = playerName;

        Random newNumber = new Random();
        this.targetNumber = newNumber.nextInt(rangeMax - rangeMin + 1) + rangeMin;

        this.attemptsMade = 0;
        this.isOver = false;
        this.isWon = false;
        this.score = 0;
    }

    public String makeGuess(int playerInput) {
        if (isOver || attemptsMade >= maxAttempts) {
            return "Game is already over. Start a new one!";
        }
        attemptsMade++;
        if (playerInput == targetNumber) {
            isWon = true;
            calculateScore();
            isOver = true;
            return "You won! " + playerName;
        } else if (attemptsMade >= maxAttempts) {
            isOver = true;
            calculateScore();
            return "Sorry "+ playerName +" You've run out of attempts! The number was " + targetNumber + ".";
        } else if (playerInput > targetNumber) {
            return "Too High. Attempts left: " + (maxAttempts - attemptsMade);
        } else {
            return "Too Low. Attempts left: " + (maxAttempts - attemptsMade);
        }
    }

    public void calculateScore() {
        if (attemptsMade == 0) score = 0;
        else this.score = maxAttempts / attemptsMade * 1000;
    }
}


