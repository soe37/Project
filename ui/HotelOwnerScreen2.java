import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class HotelOwnerScreen2 extends JFrame implements ActionListener {
    public HotelOwnerScreen2() {
        User user = Main.getLoggedInUser();
        int userId = user.getId();
        List<Hotel> hotels = Main.hotelController.getHotelsByUserId(userId);

        setTitle("HotelOwner Screen");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(hotels.size() + 2, 1));

        JLabel label = new JLabel("Welcome, " + user.getUsername());
        panel.add(label);

        for (Hotel hotel : hotels) {
            JPanel hotelPanel = new JPanel(new BorderLayout());
            JLabel hotelLabel = new JLabel(hotel.getName());
            hotelPanel.add(hotelLabel, BorderLayout.CENTER);

            JButton selectButton = new JButton("Select");
            selectButton.setActionCommand(Integer.toString(hotel.getId()));
            selectButton.addActionListener(this);
            hotelPanel.add(selectButton, BorderLayout.EAST);

            panel.add(hotelPanel);
        }



        JButton addButton = new JButton("Add Hotel");
        addButton.addActionListener(this);
        panel.add(addButton);


        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add Hotel")) {
            JTextField nameField = new JTextField(10);
            JTextField locationField = new JTextField(10);
            JTextField ratingField = new JTextField(10);

            JPanel dialogPanel = new JPanel(new GridLayout(3, 2));
            dialogPanel.add(new JLabel("Name:"));
            dialogPanel.add(nameField);
            dialogPanel.add(new JLabel("Location:"));
            dialogPanel.add(locationField);
            dialogPanel.add(new JLabel("Rating:"));
            dialogPanel.add(ratingField);

            int result;
            do {
                result = JOptionPane.showConfirmDialog(null, dialogPanel, "Enter Hotel Information",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String name = nameField.getText();
                    String location = locationField.getText();
                    String ratingText = ratingField.getText();

                    if (name.isEmpty() || location.isEmpty() || ratingText.isEmpty()) {
                        JOptionPane.showMessageDialog(dialogPanel, "Please complete all fields", "Error", JOptionPane.ERROR_MESSAGE);
                        continue;
                    }

                    try {
                        int rating = Integer.parseInt(ratingText);
                        if (rating < 1 || rating > 5) {
                            throw new NumberFormatException();
                        }
                        User user = Main.getLoggedInUser();
                        int hotelOwnerId = user.getId();
            
                        Main.hotelController.addHotel(name, location, rating, hotelOwnerId);
                        JOptionPane.showMessageDialog(null, "Successfully added!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Rating must be an integer between 1 and 5.", "Invalid Rating", JOptionPane.ERROR_MESSAGE);
                        continue;
                    }
                }
            } while (result == JOptionPane.OK_OPTION);

        } else if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            int hotelId = Integer.parseInt(button.getActionCommand());
            
            new HotelOwnerScreen(hotelId);
        }
    }
}