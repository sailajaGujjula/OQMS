package Services;
import java.util.HashMap;
import java.util.Map;
import Models.User;
public class UserDatabase {
    private Map<String, User> users;
    public UserDatabase() {
        users = new HashMap<>();
    }
    public boolean addUser(String username, String password) {
        if (users.containsKey(username)) {
            return false; // 
        }
        users.put(username, new User(username, password));
        return true; // User added successfully
    }

    public User getUser(String username) {
        return users.get(username);
    }
    public boolean isUserRegistered(String username) {
        return users.containsKey(username);
    }
}
