// CustomerScreen.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class RoomsScreen extends JFrame {

    public RoomsScreen(int hotelId) {

        List<RoomType> roomTypes = Main.roomTypeController.getRoomTypesByHotelId(hotelId);

        setTitle("Rooms Screen");
        setSize(650, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 20));

        for (RoomType roomType : roomTypes) {
            JPanel roomTypePanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;

            // Display room type name
            gbc.gridx = 0;
            JLabel roomTypeLabel = new JLabel("Room Type: " + roomType.getName());
            roomTypePanel.add(roomTypeLabel, gbc);

            // Display capacity
            gbc.gridx = 1;
            gbc.insets = new Insets(0, 10, 0, 10); // Adjust spacing here
            JLabel capacityLabel = new JLabel("Capacity: " + roomType.getCapacity());
            roomTypePanel.add(capacityLabel, gbc);

            // Display price per night
            gbc.gridx = 2;
            JLabel priceLabel = new JLabel("Price per Night: " + roomType.getPricePerNight());
            roomTypePanel.add(priceLabel, gbc);

            List<Room> rooms = Main.roomController.getRoomsByRoomTypeId(roomType.getId());

            gbc.gridx = 3;
            JLabel numberOfRoomsLabel = new JLabel("Number of rooms: " + rooms.size());
            roomTypePanel.add(numberOfRoomsLabel, gbc);


            gbc.gridx = 4; 
            JButton viewPhotosButton = new JButton("Change photos");
            viewPhotosButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openPhotoGallery(roomType.getId());
                }
            });

            roomTypePanel.add(viewPhotosButton, gbc);

            panel.add(roomTypePanel);
        }
        
        JButton addRoomTypeButton = new JButton("Add Room Type");
        addRoomTypeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                JTextField nameField = new JTextField(10);
                JTextField capacityField = new JTextField(10);
                JTextField pricePerNightField = new JTextField(10);

                JPanel dialogPanel = new JPanel(new GridLayout(3, 2));
                dialogPanel.add(new JLabel("Room Type Name:"));
                dialogPanel.add(nameField);
                dialogPanel.add(new JLabel("Capacity:"));
                dialogPanel.add(capacityField);
                dialogPanel.add(new JLabel("Price per night:"));
                dialogPanel.add(pricePerNightField);


                int result;
                do {
                    result = JOptionPane.showConfirmDialog(null, dialogPanel, "Enter Hotel Information",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (result == JOptionPane.OK_OPTION) {
                        // Retrieve entered values
                        String name = nameField.getText();
                        String capacityText = capacityField.getText();
                        String pricePerNightText = pricePerNightField.getText();

                        if (name.isEmpty() || capacityText.isEmpty() || pricePerNightText.isEmpty()) {
                            JOptionPane.showMessageDialog(dialogPanel, "Please complete all fields", "Error", JOptionPane.ERROR_MESSAGE);
                            continue; // Skip the rest of the loop and start over
                        }

                        try {
                            int capacity = Integer.parseInt(capacityText);
                            int pricePerNight = Integer.parseInt(pricePerNightText);

                            if (capacity < 1 || capacity > 1000 || pricePerNight < 1 || pricePerNight > 1000000000) {
                                throw new NumberFormatException();
                            }

                            // Add the new hotel (you need to implement this logic)
                            Main.roomTypeController.addRoomType(name, capacity, pricePerNight, hotelId);
                            JOptionPane.showMessageDialog(null, "Successfully added!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Capacity must be an integer between 1 and 1000, and price per night must be an integer between 1 and 1,000,000,000.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                            continue; // Skip the rest of the loop and start over
                        }
                    }
                } while (result == JOptionPane.OK_OPTION);
            }
        });


        panel.add(addRoomTypeButton);

        add(panel);
        setVisible(true);

        add(panel);
        setVisible(true);
        

    }

    private void openPhotoGallery(int roomTypeId) {
        File directory = new File("photos\\roomType\\" + roomTypeId);

        PhotoUploaderScreen photoUploaderScreen = new PhotoUploaderScreen(directory);
        photoUploaderScreen.setVisible(true);
    }
}
