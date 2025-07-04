package com.example.guessgame;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameService {
    private final Map<UUID, Game> games = new ConcurrentHashMap<UUID, Game>();

    @Getter
    private final List<Score> highScores = new ArrayList<>();

    public UUID startGame(int maxAttempts, int rangeMin, int rangeMax, String playerName) {
        Game newGame = new Game(maxAttempts, rangeMin, rangeMax, playerName);
        UUID gameId = newGame.getGameId();
        games.put(gameId, newGame);
        return gameId;
    }

    public GuessResponse gameGuess(UUID gameId, int guess) {

        Game foundGame = games.get(gameId);
        if (foundGame == null) {
            return new GuessResponse("No game exists", 0, GuessResponse.gameStatus.LOST,0);
        } else {
            String message = foundGame.makeGuess(guess);
            GuessResponse.gameStatus status;

            if (foundGame.isWon()) {
                status = GuessResponse.gameStatus.WON;
            } else if (foundGame.isOver()) {
                status = GuessResponse.gameStatus.LOST;
            } else {
                status = GuessResponse.gameStatus.ONGOING;
            }
            return new GuessResponse(message, foundGame.getMaxAttempts() - foundGame.getAttemptsMade(), status, foundGame.getScore());
        }

    }

    public void saveScore(int score, String playerName, String difficulty, String difficultyName) {
        Score scores = new Score(score, playerName, difficulty, difficultyName);
        highScores.add(scores);
    }

}