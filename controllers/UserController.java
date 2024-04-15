import java.util.List;

public class UserController {
    private UserDB userDB;

    public UserController(UserDB userDB) {
        this.userDB = userDB;
    }

    public User logIn(String username, String password) {
        List<User> users = userDB.getUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("User logged in successfully: " + user.getUsername());
                Main.setLoggedInUser(user);
                return user; // User found and logged in successfully
            }
        }
        System.out.println("Failed to log in. Invalid username or password.");
        return null; // User not found or invalid credentials
    }


    public boolean signUp(String username, String email, String password) {
        // Validate input parameters
        if (username == null || email == null || password == null) {
            // Invalid input, return false
            System.out.println("Failed to sign up");

            return false;
        }
        
        // Check if the username or email is already taken
        if (userDB.isUsernameTaken(username) || userDB.isEmailTaken(email)) {
            // Username or email is already taken, return false
            System.out.println("Failed to sign up");

            return false;
        }

        // Save the new user to the data storage
        userDB.saveUser(username, email, password);

        System.out.println("Sign up success");

        return true;
    }

    public List<User> getUsers() {

        List<User> users = userDB.getUsers();
        return users;
    }


    public void removeUser(int id) {
        userDB.removeUser(id);
    }

    public void updateUserType(int id, int newType) {
        userDB.updateUserType(id, newType);
    }

}
