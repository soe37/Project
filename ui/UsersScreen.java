import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersScreen extends JFrame {
    private JPanel userPanel;

    public UsersScreen() {
        setTitle("Users Screen");
        setSize(400, 300);
        setLocationRelativeTo(null);

        userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));

        refreshPanel();

        JScrollPane scrollPane = new JScrollPane(userPanel);
        add(scrollPane);

        setVisible(true);
    }

    private JPanel createUserDetailsPanel(User user) {
        JPanel userDetailsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel usernameLabel = new JLabel("Username: " + user.getUsername());
        JLabel emailLabel = new JLabel("Email: " + user.getEmail());

        int type = user.getType();

        Map<Integer, String>typeMap = new HashMap<>();
        typeMap.put(0, "Customer"); // Map type IDs to type names
        typeMap.put(1, "Hotel Owner");
        typeMap.put(2, "Webmaster");


        String typeName = typeMap.getOrDefault(type, "Unknown");

        JLabel typeLabel = new JLabel("Type: " + typeName);

        JButton removeButton = new JButton("Remove");
        JButton updateTypeButton = new JButton("Update Type");

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.userController.removeUser(user.getId());
                refreshPanel();
            }
        });

        updateTypeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Define the options
                Object[] options = { "Customer", "Hotel Owner", "Webmaster" };

                // Prompt the user to select an option
                int choice = JOptionPane.showOptionDialog(
                    UsersScreen.this,
                    "Choose new type for user " + user.getUsername(),
                    "Update User Type",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

                // Handle the user's choice
                if (choice >= 0) { // User selected an option
                    // Update user type based on the choice
                    int newType = choice;
                    Main.userController.updateUserType(user.getId(), newType);
                    refreshPanel(); // Refresh the panel after updating
                }
            }
        });



        userDetailsPanel.add(usernameLabel);
        userDetailsPanel.add(emailLabel);
        userDetailsPanel.add(typeLabel);
        userDetailsPanel.add(removeButton);
        userDetailsPanel.add(updateTypeButton);

        return userDetailsPanel;
    }

    private void refreshPanel() {
        userPanel.removeAll();
        List<User> users = Main.userController.getUsers();

        for (User user : users) {
            JPanel userDetailsPanel = createUserDetailsPanel(user);
            userPanel.add(userDetailsPanel);
        }

        userPanel.revalidate();
        userPanel.repaint();
    }
}