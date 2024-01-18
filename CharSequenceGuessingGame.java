import java.util.Random;
import java.util.Scanner;

public class CharSequenceGuessingGame {

    static String difficulty;
    static long timeLimit;
    static char generateRandomChar() {
        return (char) ('A' + new Random().nextInt(26));
    }

    static char wrapAround(char c) {
        if (c > 'Z') {
            return (char) ('A' + (c - 'Z' - 1));
        }
        return c;
    }

    static char generateNextChar(char c1, int difference) {
        char nextChar = (char) (c1 + difference);
        return wrapAround(nextChar);
    }

    static void generateDifference(int[] difference) {
        difference[0] = new Random().nextInt(4) + 1;
        difference[1] = new Random().nextInt(4) + 1;
        difference[2] = new Random().nextInt(6) + 1;
    }

    static void generateString(char c1, StringBuilder[] s) {
        int[] difference = new int[3];
        generateDifference(difference);
        char[] nextChar = new char[9];

        for (int i = 0; i < 9; i++) {
            if (i == 0) nextChar[i] = c1;
            else if (i == 3 || i == 6) nextChar[i] = generateNextChar(nextChar[i - 1], difference[2]);
            else if (i == 1 || i == 4 || i == 7) nextChar[i] = generateNextChar(nextChar[i - 1], difference[0]);
            else if (i == 2 || i == 5 || i == 8) nextChar[i] = generateNextChar(nextChar[i - 1], difference[1]);

            if (i < 3) {
                s[0].append(wrapAround(nextChar[i]));
            } else if (i < 6) {
                s[1].append(wrapAround(nextChar[i]));
            } else {
                s[2].append(wrapAround(nextChar[i]));
            }
        }
    }

    public static void startGame() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Character Sequence Guessing Game");
            System.out.println("--------------------------------");

            int numRounds = 5;
            int currentRound = 1;
            int score = 0;

            int option = Difficulty.selectDifficulty();
            switch (option) {
                case 1:
                    difficulty = "easy";
                    timeLimit = 20000 * numRounds;
                    break;
                case 2:
                    difficulty = "medium";
                    timeLimit = 10000 * numRounds;
                    break;
                case 3:
                    difficulty = "hard";
                    timeLimit = 5000 * numRounds;
                    break;
            }


            long start_time = System.currentTimeMillis();

            while (currentRound <= numRounds && System.currentTimeMillis() - start_time < timeLimit) {
                char c1 = generateRandomChar();
                StringBuilder[] s = new StringBuilder[3];
                for (int i = 0; i < s.length; i++) {
                    s[i] = new StringBuilder();
                }

                generateString(c1, s);

                System.out.println("Round " + currentRound + " - Guess the next string based on: " + s[0] + ", " + s[1]);

//            long start_time = System.currentTimeMillis();

                System.out.print("Your guess: ");
                String userGuess = scanner.next();

                long end_time = System.currentTimeMillis();
                long elapsed_time = end_time - start_time;

                if (userGuess.equals(s[2].toString())) {
                    System.out.println("Correct! Next round...");
                    score++;
                } else {
                    System.out.println("Incorrect. The correct answer was: " + s[2]);
                }
                currentRound++;
            }

            System.out.println("Your score is " + score);

            Leaderboard leaderboard = new Leaderboard("CharSequenceGuessingGame");
            leaderboard.addScore(difficulty, GamesMenu.currentUser.getUsername(), score);
            leaderboard.displayLeaderboard(difficulty);

            System.out.println("Do you want to play again? (y/n)");
            scanner.nextLine(); // Consume the newline character
            String playAgain = scanner.nextLine().trim().toLowerCase();
            if (!playAgain.equals("y")) {
                GamesMenu.start(GamesMenu.currentUser);
                break;
            }

        }
    }
}