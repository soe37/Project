import java.util.List;

public class UserController {
    private UserDB userDB;

    public UserController(UserDB userDB) {
        this.userDB = userDB;
    }


    // Logs in a user with the provided username and password.
    //
    // #1
    // Retrieves a list of all users from the database.
    //
    // #2  
    // Iterates through each user in the list.
    //
    // #3
    // Checks if the username and password of the current user 
    // match the provided username and password.
    //
    // #4
    // If no user with matching credentials is found, prints a failure message and returns null.
    public User logIn(String username, String password) {
        // 1
        List<User> users = userDB.getUsers();
        
        // 2
        for (User user : users) {
            // 3
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                // 4
                Main.setLoggedInUser(user);
                return user;
            }
        }

        // 4
        return null;
    }


    // Signs up a new user with the provided username, email, and password.
    // #1
    // Checks if any of the input parameters (username, email, password) is null.
    // If any parameter is null, prints a failure message and returns false.
    //
    // #2
    // Checks if the provided username or email is already taken by another user.
    // If either the username or email is taken, prints a failure message and returns false.
    //
    // #3
    // If the username and email are available, saves the new user to the database with the provided details.
    //
    public boolean signUp(String username, String email, String password) {
        
        // 1
        if (username == null || email == null || password == null) {
            return false;
        }
        

        // 2
        if (userDB.isUsernameTaken(username) || userDB.isEmailTaken(email)) {
            return false;
        }

        // 3
        userDB.saveUser(username, email, password);

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
