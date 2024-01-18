import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Leaderboard {
    private List<Score> scores;
    private String game;

    public Leaderboard(String game) {
        this.game = game;
        this.scores = new ArrayList<>();
        loadScoresFromFile();
    }

    public void addScore(String difficulty, String player, int score) {
        scores.add(new Score(game, difficulty, player, score));
        scores.sort(Comparator.comparingInt(Score::getScore).reversed());
        saveScoresToFile();
    }

    public List<Score> getTopScores(String difficulty, int topN) {
        List<Score> topScores = new ArrayList<>();
        for (Score score : scores) {
            if (score.getDifficulty().equals(difficulty)) {
                topScores.add(score);
                if (topScores.size() == topN) {
                    break;
                }
            }
        }
        return topScores;
    }

    public void displayLeaderboard(String difficulty) {
        System.out.println("Leaderboard for " + game + " (" + difficulty + "):");
        List<Score> topScores = getTopScores(difficulty, 10);
        for (int i = 0; i < topScores.size(); i++) {
            System.out.println((i + 1) + ". " + topScores.get(i).getPlayer() + ": " + topScores.get(i).getScore());
        }
    }

    private void saveScoresToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(game + "_leaderboard.txt"))) {
            for (Score score : scores) {
                writer.println(score.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadScoresFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(game + "_leaderboard.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scores.add(Score.fromString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static void viewLeaderboard(Scanner scanner) {
        System.out.println("Choose a game:");
        System.out.println("1. Memory Game");
        System.out.println("2. Letter Index Game");
        System.out.println("3. Linear Equation Game");
        System.out.println("4. Random Arrow Game");
        System.out.println("5. Character Sequence Guessing Game");
        int gameChoice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.println("Choose a difficulty:");
        System.out.println("1. Easy");
        System.out.println("2. Medium");
        System.out.println("3. Hard");
        int difficultyChoice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        String gameName;
        String difficulty ;

        switch (gameChoice) {
            case 1:
                gameName = "MemoryGame";
                break;
            case 2:
                gameName = "Letter Index Game";
                break;
            case 3:
                gameName = "Linear Equation Game";
                break;
            case 4:
                gameName = "Random Arrow Game";
                break;
            case 5:
                gameName = "CharSequenceGuessingGame";
                break;
            default:
                System.out.println("Invalid game choice. Please choose again.");
                return;
        }

        switch (difficultyChoice) {
            case 1:
                difficulty = "easy";
                break;
            case 2:
                difficulty = "medium";
                break;
            case 3:
                difficulty = "hard";
                break;
            default:
                System.out.println("Invalid difficulty choice. Please choose again.");
                return;
        }

        Leaderboard leaderboard = new Leaderboard(gameName);
        leaderboard.displayLeaderboard(difficulty);

        System.out.println("Do you want to see the leaderboard for another game? (y/n)");
        String choice = scanner.nextLine();
        if (choice.equals("y")) {
            viewLeaderboard(scanner);
        }

    }
}