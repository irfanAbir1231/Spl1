import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class LetterIndexGame {
    public static List<String> words = new ArrayList<>();

    // Function to generate a random word and arrow direction based on difficulty
    public static Pair<String, Character> generateRandomQuestion(String difficulty) {
        Random random = new Random();
        int randomIndex = random.nextInt(words.size());
        String word = words.get(randomIndex);

        char arrow;
        if ("easy".equalsIgnoreCase(difficulty)) {
            arrow = (random.nextInt(2) == 0) ? '<' : '>';
        } else if ("medium".equalsIgnoreCase(difficulty)) {
            arrow = (random.nextInt(3) == 0) ? '<' : (random.nextInt(2) == 0) ? '>' : '/';
        } else if ("hard".equalsIgnoreCase(difficulty)) {
            int signIndex = random.nextInt(4);
            switch (signIndex) {
                case 0:
                    arrow = '<';
                    break;
                case 1:
                    arrow = '>';
                    break;
                case 2:
                    arrow = '/';
                    break;
                case 3:
                    arrow = '!';
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + signIndex);
            }
        } else {
            throw new IllegalArgumentException("Invalid difficulty level");
        }

        return new Pair<>(word, arrow);
    }

    public static void loadWordsFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startGame(){
        loadWordsFromFile("wordList.txt");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Letter Index Game");
        System.out.println("-----------------");
        System.out.println("Instructions:");
        System.out.println("1. A word will be displayed on the screen.");
        System.out.println("2. You have to enter the letter indicated by the symbol.");
        System.out.println("3. > indicates the 2nd letter, < indicates the 3rd letter, / indicates the 4th letter and ! indicates a 5th letter.");

        while (true) {
            int option = Difficulty.selectDifficulty();

            String difficulty;
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
                default:
                    System.out.println("Invalid option. Exiting...");
                    return;
            }



            long startTime = System.currentTimeMillis();
            long currentTime = startTime;
            int score = 0;
            final int gameDuration = 6000; // 1 minute in milliseconds -----------change to 60000

            while (currentTime - startTime < gameDuration) {
                Pair<String, Character> question = generateRandomQuestion(difficulty);

                System.out.println(question.getFirst() + "(" + question.getSecond() + ")");
                char answer = scanner.next().charAt(0);

                if ((question.getSecond() == '>' && answer == question.getFirst().charAt(1)) ||
                        (question.getSecond() == '<' && answer == question.getFirst().charAt(2)) ||
                        (question.getSecond() == '/' && answer == question.getFirst().charAt(3)) ||
                        (question.getSecond() == '!' && answer == question.getFirst().charAt(4))) {
                    score++;
                }
                currentTime = System.currentTimeMillis();
            }

            System.out.println("Game over! Your score is: " + score + " points.");
            Leaderboard leaderboard = new Leaderboard("Letter Index Game");
            leaderboard.addScore(difficulty, GamesMenu.currentUser.getUsername(), score);
            leaderboard.displayLeaderboard(difficulty);

                System.out.println("Do you want to play again? (y/n)");
                scanner.nextLine(); // Consume the newline character
                String playAgain = scanner.nextLine().trim().toLowerCase();
                if (!playAgain.equals("y")) {
                    GamesMenu.start(GamesMenu.currentUser);
                    break; // If the user doesn't want to play again, break the loop
                }
        }
    }
}

class Pair<T, U> {
    private T first;
    private U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }
}