import java.util.Scanner;

public class GamesMenu {
    static User currentUser;

    public static void start(User user) {
        currentUser = user;

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nGames Menu:");
            System.out.println("1. Memory Game");
            System.out.println("2. Letter Index Game");
            System.out.println("3. Linear Equation Game");
            System.out.println("4. Random Arrow Game");
            System.out.println("5. Character Sequence Guessing Game");
            System.out.println("6. View Leaderboard");
            System.out.println("7. Logout");
            System.out.print("Choose a game, view leaderboard or logout: ");

            int gameChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (gameChoice) {
                case 1:
                    MemoryGame.startGame();
                    break;
                case 2:
                    LetterIndexGame.startGame();
                    break;
                case 3:
                    LinearEquationGame.startGame();
                    break;
                case 4:
                    RandomArrowGame.startGame();
                    break;
                case 5:
                    CharSequenceGuessingGame.startGame();
                    break;
                case 6:
                    Leaderboard.viewLeaderboard(scanner);
                    break;
                case 7:
                    currentUser = null;
                    System.out.println("Logout successful!");
                    return;
                default:
                    System.out.println("Invalid choice. Please choose again.");
            }
        }
    }
}