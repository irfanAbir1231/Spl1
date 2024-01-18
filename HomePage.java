import java.util.Scanner;

public class HomePage {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserDatabase userDatabase = new UserDatabase();

        // Load existing users from file
        userDatabase.loadUsersFromFile();

        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    User loggedInUser = login(userDatabase, scanner);
                    if (loggedInUser != null) {
                        GameRecommendation.recommend(loggedInUser);
//                        PlayGames.start(loggedInUser);------------------------------------------------------
                    }
                    break;
                case 2:
                    register(userDatabase, scanner);
                    userDatabase.saveUsersToFile();
                    break;
                case 3:
                    userDatabase.saveUsersToFile();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please choose again.");
            }
        }
    }
    private static User login(UserDatabase userDatabase, Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (userDatabase.loginUser(username, password)) {
            System.out.println("Login successful!");
            return userDatabase.getUsers().stream()
                    .filter(user -> user.getUsername().equals(username))
                    .findFirst()
                    .orElse(null);
        } else {
            System.out.println("Invalid username or password. Please try again.");
            return null;
        }
    }

    private static void register(UserDatabase userDatabase, Scanner scanner) {
        System.out.print("Enter new username: ");
        String username = scanner.nextLine();
        System.out.print("Enter new password: ");
        String password=scanner.nextLine();
        System.out.print("Enter your age:");
        Integer age = scanner.nextInt();

        User newUser = new User(username, password, age);
        userDatabase.registerUser(newUser);

        System.out.println("Registration successful! Now you can login.");
    }
}
