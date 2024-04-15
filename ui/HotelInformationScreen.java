import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class HotelInformationScreen extends JFrame {
    private int hotelId;
    private JLabel nameLabel;
    private JLabel locationLabel;
    private JLabel ratingLabel;

    public HotelInformationScreen(int hotelId) {
        this.hotelId = hotelId;

        setTitle("HotelInformation Screen");
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        Hotel hotel = Main.hotelController.getHotelById(hotelId);

        String currentName = hotel.getName();
        nameLabel = new JLabel("Current Name: " + currentName);
        panel.add(nameLabel);

        JButton changeNameButton = new JButton("Change Name");
        changeNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeName();
            }
        });
        panel.add(changeNameButton);

        String currentLocation = hotel.getLocation();
        locationLabel = new JLabel("Current Location: " + currentLocation);
        panel.add(locationLabel);

        JButton changeLocationButton = new JButton("Change Location");
        changeLocationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeLocation();
            }
        });
        panel.add(changeLocationButton);

        
        int currentRating = hotel.getRating();
        ratingLabel = new JLabel("Current Rating: " + currentRating);
        panel.add(ratingLabel);

        JButton changeRatingButton = new JButton("Change Rating");
        changeRatingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeRating();
            }
        });
        panel.add(changeRatingButton);

        JButton viewPhotosButton = new JButton("Change photos");
        viewPhotosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openPhotoGallery();
            }
        });
        panel.add(viewPhotosButton);

        add(panel);
        setVisible(true);
    }

    private void openPhotoGallery() {
        File directory = new File("photos\\hotel\\" + hotelId);

        PhotoUploaderScreen photoUploaderScreen = new PhotoUploaderScreen(directory);
        photoUploaderScreen.setVisible(true);
    }

    private void changeName() {
        // Implement logic to change the name
        String newName = JOptionPane.showInputDialog(null, "Enter new name:", "Change Name", JOptionPane.QUESTION_MESSAGE);
        
        if (newName != null) {
            if (!newName.isEmpty()) {
                // Update the name in the database
                Main.hotelController.updateName(hotelId, newName);
                nameLabel.setText("Current Name: " + newName);
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a new name.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void changeLocation() {
        // Implement logic to change the location
        String newLocation = JOptionPane.showInputDialog(null, "Enter new location:", "Change Location", JOptionPane.QUESTION_MESSAGE);
        
        if (newLocation != null) {
            if (!newLocation.isEmpty()) {
                // Update the location in the database
                Main.hotelController.updateLocation(hotelId, newLocation);
                locationLabel.setText("Current Location: " + newLocation);
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a new location.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void changeRating() {
        // Implement logic to change the rating
        String newRatingStr = JOptionPane.showInputDialog(null, "Enter new rating:", "Change Rating", JOptionPane.QUESTION_MESSAGE);
        
        if (newRatingStr != null) {
            if (!newRatingStr.isEmpty()) {
                try {
                    int newRating = Integer.parseInt(newRatingStr);
                    if (newRating >= 1 && newRating <= 5) {
                        // Update the rating in the database
                        Main.hotelController.updateRating(hotelId, newRating);
                        ratingLabel.setText("Current Rating: " + newRating);
                    } else {
                        JOptionPane.showMessageDialog(null, "Rating must be an integer between 1 and 5.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid rating format. Please enter an integer.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a new rating.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
