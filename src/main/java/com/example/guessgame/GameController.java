package com.example.guessgame;


import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/game")

public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start")
    public UUID startNewGame(@RequestParam int maxAttempts, @RequestParam int rangeMin, @RequestParam int rangeMax, @RequestParam String playerName) {
        return gameService.startGame(maxAttempts, rangeMin, rangeMax, playerName);
    }
    @PostMapping("/guess")
    public GuessResponse makeGuess(@RequestBody GuessRequest request) {
        return gameService.gameGuess(request.getGameID(), request.getGuess());
    }
}
