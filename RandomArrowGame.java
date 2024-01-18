import java.util.Random;
import java.util.Scanner;

public class RandomArrowGame {
    static String difficulty;


    public static void startGame() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Random Arrow Game");
        System.out.println("-----------------");
        System.out.println("Instructions:");
        System.out.println("1. You will be shown a sequence of arrows.");
        System.out.println("2. You have to press the arrow key that corresponds to the middle arrow of the middle row.");

        while (true) {
            long startTime = System.currentTimeMillis();
            long endTime = startTime + 10000;  // 1 minute -----------------change to 60000----------------

            int score = 0;

            int option = Difficulty.selectDifficulty();

            while (System.currentTimeMillis() < endTime) {
                String randomArrows = generateRandomArrows(option);
                System.out.println(randomArrows);

                // Get user input
//                System.out.print("Input: ");
                String userInput = scanner.nextLine().trim();

                // Check correctness
                if (isValidInput(userInput) && userInput.equals(getMiddleArrow(randomArrows))) {
                    System.out.println("Correct!");
                    score++;
                } else {
                    System.out.println("Incorrect!");
                }
            }

            switch (option) {
                case 1:
                    difficulty = "easy";
                    break;
                case 2:
                    difficulty = "medium";
                    break;
                case 3:
                    difficulty = "hard";
                    break;
            }

            System.out.println("\nGame Over! Your score: " + score);
            Leaderboard leaderboard = new Leaderboard("Random Arrow Game");
            leaderboard.addScore(difficulty, GamesMenu.currentUser.getUsername(), score);
            leaderboard.displayLeaderboard(difficulty);

            System.out.println("Do you want to play again? (y/n)");
//            scanner.nextLine(); // Consume the newline character
            String playAgain = scanner.nextLine().trim().toLowerCase();
            if (!playAgain.equals("y")) {
                GamesMenu.start(GamesMenu.currentUser);
                break; // If the user doesn't want to play again, break the loop
            }
        }
    }


    public static String generateRandomArrows(int difficulty) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        int lines = 1; // Default to 1 line for easy difficulty
        if (difficulty == 2) {
            lines = 3; // 3 lines for medium difficulty
        } else if (difficulty == 3) {
            lines = 5; // 5 lines for hard difficulty
        }

        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < 5; j++) { // Each line has 5 arrows
                int randomDirection = random.nextInt(3);
                char arrow = (randomDirection == 0) ? '>' : '<';
                sb.append(arrow);
            }
            sb.append("\n"); // Add a newline character at the end of each line
        }

        return sb.toString();
    }

    public static String getMiddleArrow(String arrows) {
        String[] lines = arrows.split("\n");
        String targetLine;

        if (lines.length == 1) {
            targetLine = lines[0]; // For easy difficulty, there's only one line
        } else if (lines.length == 3) {
            targetLine = lines[1]; // For medium difficulty, choose the second line
        } else {
            targetLine = lines[2]; // For hard difficulty, choose the third line
        }

        return String.valueOf(targetLine.charAt(targetLine.length() / 2));
    }

    public static boolean isValidInput(String input) {
        return input.length() == 1 && (input.equals(">") || input.equals("<"));
    }
}