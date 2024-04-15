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
                // Handle sign-up logic here
                String username = usernameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());


                boolean success = Main.userController.signUp(username, email, password);

                if (success) {
                    // For simplicity, just display the entered values
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
