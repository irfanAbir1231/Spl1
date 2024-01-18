import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class LinearEquationGame {
    static String difficulty;
    static int generateRandomNumber(int lower, int upper) {
        return new Random().nextInt(upper - lower + 1) + lower;
    }

    static class LinearCoefficients {
        int a;
        int b;
        int c;
        int d;
    }

    static LinearCoefficients generateLinearCoefficients(int option) {
        LinearCoefficients coefficients = new LinearCoefficients();
        switch (option) {
            case 1: // Easy
                coefficients.a = generateRandomNumber(1, 5);
                coefficients.b = generateRandomNumber(1, 5);
                coefficients.c = generateRandomNumber(1, 10);
                coefficients.d = generateRandomNumber(1, 100);
                difficulty = "easy";
                break;
            case 2: // Medium
                coefficients.a = generateRandomNumber(1, 10);
                coefficients.b = generateRandomNumber(1, 10);
                coefficients.c = generateRandomNumber(1, 20);
                coefficients.d = generateRandomNumber(1, 500);
                difficulty = "medium";
                break;
            case 3: // Hard
                coefficients.a = generateRandomNumber(1, 20);
                coefficients.b = generateRandomNumber(1, 20);
                coefficients.c = generateRandomNumber(1, 40);
                coefficients.d = generateRandomNumber(1, 1000);
                difficulty = "hard";
                break;
        }
        return coefficients;
    }


    public static void startGame() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int option = Difficulty.selectDifficulty();

            long startTime = System.currentTimeMillis();
            long currentTime = startTime;
            int totalQuestions = 0;
            int correctAnswers = 0;
            int gameDuration = 10 * 1000; // 1 minute in milliseconds--------- Change to 60000---------------------

            while (currentTime - startTime < gameDuration) {
                LinearCoefficients coefficients = generateLinearCoefficients(option);
                int a = coefficients.a;
                int b = coefficients.b;
                int c = coefficients.c;
                int d = coefficients.d;

//            int d = generateRandomNumber(1, 1000);

                System.out.println("Solve: " + a + "x + " + b + "*" + c + " = " + d);

                try {
                    System.out.print("Your answer: ");
                    float userAnswer = scanner.nextFloat();

                    float correctAnswer = (float) (d - b * c) / a;

                    if (Math.abs(userAnswer - correctAnswer) < 0.1f) {
                        System.out.println("Correct!");
                        correctAnswers++;
                    } else {
                        System.out.println("Incorrect. The correct answer is: " + String.format("%.1f", correctAnswer));
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine(); // Discard the invalid input
                    continue; // Skip the rest of this iteration and continue with the next one
                }

                totalQuestions++;

                try {
                    Thread.sleep(1000); // Pause for 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                currentTime = System.currentTimeMillis();
            }

            int totalMarks = correctAnswers;
            System.out.println("\nTime's up! Total Marks: " + totalMarks + " out of " + (totalQuestions * 5));
            Leaderboard leaderboard = new Leaderboard("Linear Equation Game");
            leaderboard.addScore(difficulty, GamesMenu.currentUser.getUsername(), totalMarks);
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