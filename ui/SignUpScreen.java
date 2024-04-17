import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SignUpScreen extends JFrame {
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton signUpButton;
    private JButton loginButton;

    public SignUpScreen() {
        setTitle("Sign Up");
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        panel.add(usernameLabel);
        panel.add(usernameField);

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        panel.add(emailLabel);
        panel.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        panel.add(passwordLabel);
        panel.add(passwordField);

        signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());


                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Please complete all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

                if (!email.matches(emailRegex)) {
                    JOptionPane.showMessageDialog(panel, "Please enter a valid email address", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (username.length() > 99 || email.length() > 99 || password.length() > 99) {
                    JOptionPane.showMessageDialog(panel, "Please ensure that input fields do not exceed the maximum allowable length.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean success = Main.userController.signUp(username, email, password);

                if (success) {
                    JOptionPane.showMessageDialog(SignUpScreen.this,
                            "Username: " + username + "\n" +
                            "Email: " + email + "\n" +
                            "Password: " + password,
                            "Sign Up Successful", JOptionPane.INFORMATION_MESSAGE);
        
                    new LoginScreen();
                }
                else {
                    JOptionPane.showMessageDialog(SignUpScreen.this, "Username or Email is taken", "", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        panel.add(signUpButton);


        loginButton = new JButton("Log in");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginScreen();        
            }
        });
        panel.add(loginButton);

        add(panel);
        setVisible(true);
    }
}
