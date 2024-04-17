import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginScreen extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signUpButton;

    public LoginScreen() {
        setTitle("Login");
        setSize(300, 150);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        panel.add(usernameLabel);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        panel.add(passwordLabel);
        panel.add(passwordField);

        loginButton = new JButton("Log in");
        loginButton.addActionListener(this);
        panel.add(loginButton);

        signUpButton = new JButton("Create new account");
        signUpButton.addActionListener(this);
        panel.add(signUpButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please complete all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (username.length() > 99 || password.length() > 99) {
                JOptionPane.showMessageDialog(this, "Please ensure that input fields do not exceed the maximum allowable length.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User loggedInUser = Main.userController.logIn(username, password);

            int userType = -1;
            
            if (loggedInUser != null)
                userType = loggedInUser.getType();


            if (userType == 0) {
                new CustomerScreen();
            } else if (userType == 1) {
                new HotelOwnerScreen2();
            } else if (userType == 2) {
                new WebmasterScreen();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
        } else if (e.getSource() == signUpButton) {
            new SignUpScreen();
        }
    }
}
