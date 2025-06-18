package com.example.guessgame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import java.util.Objects;
import java.util.Scanner;

@SpringBootApplication
public class GuessgameApplication {

    private static class GameSetup {
        String playerName;
        int rangeMax;
        String difficultySettings;

        public GameSetup(String playerName, int rangeMax, String difficultySettings) {
            this.playerName = playerName;
            this.rangeMax = rangeMax;
            this.difficultySettings = difficultySettings;
        }
    }

    public static void main(String[] args) {
        int maxAttempts = 10;
        int rangeMin = 1;

        Scanner input = new Scanner(System.in);

        ConfigurableApplicationContext context = SpringApplication.run(GuessgameApplication.class, args);
        GameService gameService = context.getBean(GameService.class);

        boolean exitGame = false;
        int choice;

        do {

            System.out.println("\n--- Guessing Game Menu ---");
            System.out.println("1. Play New Game");
            System.out.println("2. Show High Scores");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1 -> {
                    GameSetup setup = gameSetup(input);
                    System.out.println(setup.playerName + " Lets start you have 10 attempts. " + setup.difficultySettings);
                    java.util.UUID currentGameId = gameService.startGame(maxAttempts, rangeMin, setup.rangeMax, setup.playerName);

                    GuessResponse gameGuess;
                    do {
                        gameGuess = gameService.gameGuess(currentGameId, input.nextInt());
                        System.out.println(gameGuess.message);
                    }
                    while (gameGuess.status == GuessResponse.gameStatus.ONGOING);
                    if (gameGuess.status == GuessResponse.gameStatus.LOST || gameGuess.status == GuessResponse.gameStatus.WON) {
                        System.out.println("Your score is "+ gameGuess.score + " points");
                    }
                }

                case 2 -> System.out.println("High Scores not implemented yet.");

                case 3 -> {
                    exitGame = true;
                    System.out.println("GoodBye!");
                }
                default -> System.out.println("Please enter number between 1-3");
            }

        }
        while (!exitGame);
        input.close();
        context.close();

    }

    private static GameSetup gameSetup(Scanner input) {
        String playerName;
        String difficulty;
        String difficultySettings = null;
        int rangeMax = 0;

        System.out.println("What is your name?");
        playerName = input.nextLine();

        boolean validInput;
        do {
            System.out.println("Good " + playerName + ", now let's choose the difficulty: easy, medium, or hard. For example type: \"medium\"");
            difficulty = input.nextLine();

            validInput = false;

            if (Objects.equals(difficulty.toLowerCase(), "easy")) {
                rangeMax = 20;
                difficultySettings = "The number can be between 1-20";
                validInput = true;
            } else if (Objects.equals(difficulty.toLowerCase(), "medium")) {
                rangeMax = 40;
                difficultySettings = "The number can be between 1-40";
                validInput = true;
            } else if (Objects.equals(difficulty.toLowerCase(), "hard")) {
                rangeMax = 100;
                difficultySettings = "The number can be between 1-100";
                validInput = true;
            } else {
                System.out.println("Invalid input. Please enter 'easy', 'medium', or 'hard'.");

            }
        } while (!validInput);

        return new GameSetup(playerName, rangeMax, difficultySettings);
    }

}
