import java.util.InputMismatchException;
import java.util.Scanner;

public class Difficulty {
    public static int selectDifficulty() {
        Scanner scanner = new Scanner(System.in);
        int difficulty;
        while (true) {
            System.out.println("Select the difficulty level:");
            System.out.println("1. Easy");
            System.out.println("2. Medium");
            System.out.println("3. Hard");
            System.out.print("Enter your choice (1/2/3): ");
            try {
                difficulty = scanner.nextInt();
                if (difficulty >= 1 && difficulty <= 3) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
        return difficulty;
    }
}
