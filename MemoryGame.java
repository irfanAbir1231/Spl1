import java.util.Scanner;
import java.util.Random;

public class MemoryGame {

    static final int EASY_GRID_SIZE = 3;
    static final int MEDIUM_GRID_SIZE = 3;
    static final int HARD_GRID_SIZE = 4;

    static final int EASY_SHOW_TIME_SECONDS = 3;
    static final int MEDIUM_SHOW_TIME_SECONDS = 5;
    static final int HARD_SHOW_TIME_SECONDS = 7;

    static int[][] gameGrid;
    static int[] shuffledNumbers;

    static String difficulty;
    static int score = 0;
    static void initializeGame(int gridSize) {
        shuffledNumbers = new int[gridSize * gridSize];
        for (int i = 0; i < gridSize * gridSize; ++i) {
            shuffledNumbers[i] = i + 1;
        }

        Random random = new Random();
        for (int i = 0; i < gridSize * gridSize; ++i) {
            int j = random.nextInt(gridSize * gridSize);
            int temp = shuffledNumbers[i];
            shuffledNumbers[i] = shuffledNumbers[j];
            shuffledNumbers[j] = temp;
        }

        gameGrid = new int[gridSize][gridSize];
        for (int i = 0; i < gridSize; ++i) {
            for (int j = 0; j < gridSize; ++j) {
                gameGrid[i][j] = shuffledNumbers[i * gridSize + j];
            }
        }
    }

    static void renderGame(int gridSize, boolean reveal) {
        for (int i = 0; i < gridSize; ++i) {
            for (int j = 0; j < gridSize; ++j) {
                if (reveal) {
                    System.out.print(gameGrid[i][j] + "\t");
                } else {
                    System.out.print("X\t");
                }
            }
            System.out.println();
        }
    }

    static void showCardsForDuration(int gridSize, int showTimeSeconds) {
        renderGame(gridSize, true);
        System.out.println("Remember the numbers for " + showTimeSeconds + " seconds...");

        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime) < showTimeSeconds * 1000) {
        }


        clearScreen();
        renderGame(gridSize, false);
    }

    static void guessCards(int gridSize) {
        Scanner scanner = new Scanner(System.in);
        int score = 0;

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < gridSize; ++i) {
            for (int j = 0; j < gridSize; ++j) {
                System.out.print("Enter your guess for row " + (i + 1) + ", column " + (j + 1) + ": ");
                int guess = scanner.nextInt();

                if (guess == gameGrid[i][j]) {
                    System.out.println("Correct!");
                    score++;
                } else {
                    System.out.println("Wrong!");
                }
            }
        }

        long endTime = System.currentTimeMillis();
        int elapsedTime = (int) ((endTime - startTime) / 1000);

        System.out.println("Your score is " + score);
        System.out.println("Your time is " + elapsedTime + " seconds");
        Leaderboard leaderboard= new Leaderboard("MemoryGame");
        leaderboard.addScore(difficulty, GamesMenu.currentUser.getUsername(), score);
        leaderboard.displayLeaderboard(difficulty);
    }

    static void guessCardsHard(int gridSize) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        long startTime = System.currentTimeMillis();

        for (int turn = 1; turn <= gridSize * gridSize; turn++) {
            int randomRow = random.nextInt(gridSize);
            int randomCol = random.nextInt(gridSize);

            System.out.print("Turn " + turn + ": Guess the number at row " + (randomRow + 1) +
                    ", column " + (randomCol + 1) + ": ");
            int guess = scanner.nextInt();

            if (guess == gameGrid[randomRow][randomCol]) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Wrong! The correct number was: " + gameGrid[randomRow][randomCol]);
            }
        }

        long endTime = System.currentTimeMillis();
        int elapsedTime = (int) ((endTime - startTime) / 1000);

        System.out.println("Your score is " + score);
        System.out.println("Your time is " + elapsedTime + " seconds");

        Leaderboard leaderboard= new Leaderboard("MemoryGame");
        leaderboard.addScore(difficulty, GamesMenu.currentUser.getUsername(), score);
        leaderboard.displayLeaderboard(difficulty);
    }

    public static void startGame() {
        Scanner scanner = new Scanner(System.in);



        System.out.println("Welcome to the Memory Game!");

        int gridSize, showTimeSeconds;
        int option;
        while (true) {
            option = Difficulty.selectDifficulty();

            while (true) {
                switch (option) {
                    case 1:
                        gridSize = EASY_GRID_SIZE;
                        showTimeSeconds = EASY_SHOW_TIME_SECONDS;
                        difficulty = "easy";
                        break;
                    case 2:
                        gridSize = MEDIUM_GRID_SIZE;
                        showTimeSeconds = MEDIUM_SHOW_TIME_SECONDS;
                        difficulty = "medium";
                        break;
                    case 3:
                        gridSize = HARD_GRID_SIZE;
                        showTimeSeconds = HARD_SHOW_TIME_SECONDS;
                        difficulty = "hard";
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        continue;
                }
                break; // If a valid option is chosen, break the loop
            }

            initializeGame(gridSize);
            scanner.nextLine(); // Consume the newline character
            System.out.println("Press Enter to reveal the numbers for " + showTimeSeconds + " seconds...");
            scanner.nextLine();
            showCardsForDuration(gridSize, showTimeSeconds);

            System.out.println("Now, guess the positions of the numbers:");
//        guessCards(gridSize);
            if (option == 1) {
                guessCards(gridSize);
            } else {
                guessCardsHard(gridSize);
            }

            System.out.println("Do you want to play again? (y/n)");
            String playAgain = scanner.nextLine().trim().toLowerCase();
            if (!playAgain.equals("y")) {
                GamesMenu.start(GamesMenu.currentUser);
                break;
            }

        }

    }

    public static void clearScreen() {
        for (int i = 0; i < 50; ++i) System.out.println();
    }

}