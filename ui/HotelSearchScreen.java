import javax.swing.*;
import java.awt.*;
import java.io.File;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.List;

public class HotelSearchScreen extends JFrame {
    private JSpinner minRatingSpinner;
    private JSpinner maxRatingSpinner;
    private JList<String> locationsList;

    private JPanel resultsPanel;
    private GridBagConstraints gbc;

    public HotelSearchScreen() {
        setTitle("Hotel Search Screen");
        setSize(600, 400); // Increased size for better visibility
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel minRatingLabel = new JLabel("Minimum Rating:");
        SpinnerNumberModel minRatingModel = new SpinnerNumberModel(1, 1, 5, 1);
        minRatingSpinner = new JSpinner(minRatingModel);

        JLabel maxRatingLabel = new JLabel("Maximum Rating:");
        SpinnerNumberModel maxRatingModel = new SpinnerNumberModel(5, 1, 5, 1);
        maxRatingSpinner = new JSpinner(maxRatingModel);

        JLabel locationsLabel = new JLabel("Locations:");
        String[] locations = Main.hotelController.getDistinctHotelLocations();
        locationsList = new JList<>(locations);
        locationsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane locationsScrollPane = new JScrollPane(locationsList);

        JButton searchButton = new JButton("Search");

        searchPanel.add(minRatingLabel, gbc);
        gbc.gridx++;
        searchPanel.add(minRatingSpinner, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        searchPanel.add(maxRatingLabel, gbc);
        gbc.gridx++;
        searchPanel.add(maxRatingSpinner, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        searchPanel.add(locationsLabel, gbc);
        gbc.gridx++;
        gbc.gridwidth = 2;
        searchPanel.add(locationsScrollPane, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        searchPanel.add(searchButton, gbc);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new GridBagLayout());
        JScrollPane resultsScrollPane = new JScrollPane(resultsPanel);
        resultsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(resultsScrollPane, BorderLayout.CENTER);

        minRatingSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int minRating = (int) minRatingSpinner.getValue();
                int maxRating = (int) maxRatingSpinner.getValue();
                if (minRating > maxRating) {
                    minRatingSpinner.setValue(maxRating);
                }
            }
        });

        maxRatingSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int minRating = (int) minRatingSpinner.getValue();
                int maxRating = (int) maxRatingSpinner.getValue();
                if (maxRating < minRating) {
                    maxRatingSpinner.setValue(minRating);
                }
            }
        });

        searchButton.addActionListener(e -> {
            int minRating = (int) minRatingSpinner.getValue();
            int maxRating = (int) maxRatingSpinner.getValue();
            String[] selectedLocations = locationsList.getSelectedValuesList().toArray(new String[0]);

            List<Hotel> hotelsSearchResult = Main.hotelController.hotelSearch(selectedLocations, minRating, maxRating);

            resultsPanel.removeAll();

            // Add the results to the resultsPanel
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(5, 5, 5, 5);
            for (Hotel hotel : hotelsSearchResult) {
                String hotelInfo = String.format("%s, Location: %s, Rating: %d", hotel.getName(), hotel.getLocation(), hotel.getRating());
                JLabel label = new JLabel(hotelInfo);

                JButton viewPhotosButton = new JButton("View photos");
                viewPhotosButton.addActionListener(event -> {
                    // Handle selection logic here
                    ImageGalleryScreen galleryScreen = new ImageGalleryScreen();
                    galleryScreen.setVisible(true);

                    int hotelId = hotel.getId(); // Assuming hotel is an instance of Hotel class
                    File directory = new File("photos\\hotel\\" + hotelId); // Adjust the relative path as needed
                    galleryScreen.showImagesInFolder(directory);
                });

                JButton selectButton = new JButton("Select");
                selectButton.addActionListener(event -> {
                    // Handle selection logic here
                    System.out.println("Hotel selected: " + hotel.getName());

                    new RoomSearchScreen(hotel.getId());
                });
                
                // Create a new panel for each hotel
                JPanel hotelPanel = new JPanel(new GridBagLayout());
                GridBagConstraints hotelPanelGbc = new GridBagConstraints();
                hotelPanelGbc.gridx = 0;
                hotelPanelGbc.gridy = 0;
                hotelPanel.add(label, hotelPanelGbc);
                hotelPanelGbc.gridx++;
                hotelPanel.add(Box.createHorizontalStrut(10), hotelPanelGbc); // Add some horizontal space
                hotelPanelGbc.gridx++;
                hotelPanel.add(viewPhotosButton, hotelPanelGbc);
                hotelPanelGbc.gridx++;
                hotelPanel.add(selectButton, hotelPanelGbc);
                
                // Add the hotel panel to the results panel
                resultsPanel.add(hotelPanel, gbc);
                gbc.gridy++;
            }

            // Refresh the resultsPanel
            resultsPanel.revalidate();
            resultsPanel.repaint();
        });

        add(panel);
        setVisible(true);
    }
}