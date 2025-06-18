package com.example.guessgame;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GuessResponse {
    String message;
    int attemptsLeft;
    gameStatus status;
    int score;

    enum gameStatus {
        ONGOING,
        WON,
        LOST;
    }
}
