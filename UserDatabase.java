import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    private List<User> users;

    public UserDatabase() {
        this.users = new ArrayList<>();
    }

    public void registerUser(User user) {
        users.add(user);
        saveUsersToFile();
    }

    public boolean loginUser(String username, String password) {
        return users.stream()
                .anyMatch(user -> user.getUsername().equals(username) && user.getPassword().equals(password));
    }

    void saveUsersToFile() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("users.dat"))) {
            outputStream.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    void loadUsersFromFile() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("users.dat"))) {
            users = (List<User>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Handle exceptions
        }
    }

    public List<User> getUsers() {
        return users;
    }
}