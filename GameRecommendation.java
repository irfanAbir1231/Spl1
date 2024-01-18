import java.util.Scanner;

public class GameRecommendation {

    public static void recommend(User loggedInUser) {
        User user=loggedInUser;
        GamesMenu.currentUser=loggedInUser;
        Scanner scanner = new Scanner(System.in);

        System.out.print("1. What is your sleeping hour? ");
        int sleepingHour = scanner.nextInt();
        int age=loggedInUser.getAge();
        int requiredSleepingHours = calculateRequiredSleepingHours(age);
        if (sleepingHour <= requiredSleepingHours / 2) {
            System.out.println("Suggestion: Consider getting more sleep or play a mathematical game.");
            System.out.println("Would you like to play a mathematical game? (y/n)");
            scanner.nextLine(); // Consume the newline character
            String playMathematicalGame = scanner.nextLine().trim().toLowerCase();
            if (playMathematicalGame.equals("y")) {
                LinearEquationGame.startGame();
            } else {
                GamesMenu.start(loggedInUser);
            }
        }


        System.out.print("2. What is your current mode? ");
        int currentMode = scanner.nextInt();

        System.out.print("3. What have you planned to do now?\n" +
                "   1) Memorize something\n" +
                "   2) Task that needs analytical thinking\n" +
                "   3) Task that needs logical thinking\n" +
                "   4) Handle diverse situations simultaneously\n" +
                "   5) Solve mathematical problems\n" +
                "Enter the corresponding number (1/2/3/4/5): ");
        int plannedActivity = scanner.nextInt();

        int sleepingScore = calculateSleepingScore(sleepingHour, requiredSleepingHours);

        int modePoint = calculateModePoint(currentMode);

        int sumPoint = sleepingScore + modePoint;

        String gameDifficulty = suggestGameDifficulty(sumPoint);

        System.out.println("Based on your inputs:");
        System.out.println("Suggested Game Difficulty: " + gameDifficulty);

        suggestGame(plannedActivity, user);

    }

    private static int calculateRequiredSleepingHours(int age) {
        if (age >= 6 && age <= 13) {
            return 9;
        } else if (age >= 14 && age <= 17) {
            return 8;
        } else if (age >= 18 && age <= 64) {
            return 7;
        } else {
            return 7; // Assuming the same requirement for age 65+
        }
    }

    private static int calculateSleepingScore(int sleepingHour, int requiredSleepingHours) {
        int sleepingScore = 5; // Default score

        if (sleepingHour <= requiredSleepingHours / 2) {
            System.out.println("Suggestion: Consider getting more sleep or play a mathematical game.");
        } else {
            int hourDifference = requiredSleepingHours - sleepingHour;
            sleepingScore -= hourDifference;
        }

        return sleepingScore<1? 1 : sleepingScore;
    }

    private static int calculateModePoint(int currentMode) {
        if (currentMode >= 9 && currentMode <= 10) {
            return 5;
        } else if (currentMode >= 7 && currentMode <= 8) {
            return 4;
        } else if (currentMode >= 5 && currentMode <= 6) {
            return 3;
        } else if (currentMode >= 3 && currentMode <= 4) {
            return 2;
        } else if (currentMode >= 1 && currentMode <= 2) {
            return 1;
        } else {
            return 0; // Invalid input, return 0
        }
    }

    private static String suggestGameDifficulty(int sumPoint) {
        if (sumPoint >= 9 && sumPoint <= 10) {
            return "hard";
        } else if (sumPoint >= 7 && sumPoint <= 8) {
            return "medium";
        } else {
            return "easy";
        }
    }

    private static void suggestGame(int plannedActivity, User loggedInUser) {
        Scanner scanner = new Scanner(System.in);
        switch (plannedActivity) {
            case 1:
                System.out.println("Suggestion: Memory Game");
                System.out.println("Would you like to play Memory Game? (y/n)");
                String playMemoryGame = scanner.nextLine().trim().toLowerCase();
                GamesMenu.currentUser=loggedInUser;
                if (playMemoryGame.equals("y")) {
                    MemoryGame.startGame();
                }else{
                    GamesMenu.start(loggedInUser);
                }
                break;
            case 2:
                System.out.println("Suggestion: Letter Index Game");
                System.out.println("Would you like to play Letter Index Game? (y/n)");
                String playRandomWordGame = scanner.nextLine().trim().toLowerCase();
                GamesMenu.currentUser=loggedInUser;
                if (playRandomWordGame.equals("y")) {
                    LetterIndexGame.startGame();
                }else{
                    GamesMenu.start(loggedInUser);
                }
                break;
            case 3:
                System.out.println("Suggestion: Character Sequence Guessing Game");
                System.out.println("Would you like to play Character Sequence Guessing Game? (y/n)");
                String playLogicalGame = scanner.nextLine().trim().toLowerCase();
                GamesMenu.currentUser=loggedInUser;
                if (playLogicalGame.equals("y")) {
                    CharSequenceGuessingGame.startGame();
                }else{
                    GamesMenu.start(loggedInUser);
                }
                break;
            case 4:
                System.out.println("Suggestion: Random Arrow Game");
                System.out.println("Would you like to play Random Arrow Game? (y/n)");
                String playRandomArrowGame = scanner.nextLine().trim().toLowerCase();
                GamesMenu.currentUser=loggedInUser;
                if (playRandomArrowGame.equals("y")) {
                    RandomArrowGame.startGame();
                }else{
                    GamesMenu.start(loggedInUser);
                }
                break;
            case 5:
                System.out.println("Suggestion: Linear Equation Game");
                System.out.println("Would you like to play Linear Equation Game? (y/n)");
                String playMathematicalGame = scanner.nextLine().trim().toLowerCase();
                GamesMenu.currentUser=loggedInUser;
                if (playMathematicalGame.equals("y")) {
                    LinearEquationGame.startGame();
                }else{
                    GamesMenu.start(loggedInUser);
                }
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                GamesMenu.currentUser=loggedInUser;
                GamesMenu.start(loggedInUser);
                break;
        }
    }
}